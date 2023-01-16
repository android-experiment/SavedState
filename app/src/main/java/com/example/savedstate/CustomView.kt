package com.example.savedstate

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val colors = listOf(
        Color.BLACK,
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.WHITE
    )

    private var colorIndex = 0
        set(value) {
            if (field != value) {
                field = value
                rectPaint.color = colors[value]
            }
        }

    private val rectPaint = Paint().apply {
        color = colors[colorIndex]
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            rectPaint
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                colorIndex = (colorIndex + 1) % colors.size
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            colorIndex = state.colorIndex
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState() ?: return null
        return SavedState(parcelable).apply {
            colorIndex = this@CustomView.colorIndex
        }
    }

    private class SavedState(parcelable: Parcelable) : BaseSavedState(parcelable) {

        var colorIndex = 0

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(colorIndex)
        }
    }
}
