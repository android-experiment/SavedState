## SavedState

### EditText 를 이용하여 Configuration Changed 대응 테스트
[ID 만 추가하면 상태가 저장될까?](https://developer.android.com/guide/fragments/saving-state#view)

#### CustomView에 ID를 추가하지 않은 경우
https://user-images.githubusercontent.com/9216335/212598710-3706000e-2bb0-425f-a706-f5298963f8c4.mp4  

#### CustomView에 ID를 추가한경우
https://user-images.githubusercontent.com/9216335/212598716-a6639659-b624-4254-9bfd-ce58cd9d3887.mp4  

#### CustomView 상태 저장 구현
```
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
```

### 참고자료
- [프래그먼트로 상태 저장](https://developer.android.com/guide/fragments/saving-state#view)
