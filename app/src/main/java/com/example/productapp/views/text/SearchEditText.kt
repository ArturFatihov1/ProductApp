package com.example.productapp.views.text

import android.content.Context
import android.util.AttributeSet


class SearchEditText : androidx.appcompat.widget.AppCompatEditText, UpdateText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun update(text: String) {
        if (this.text.toString() != text) {
            this.setText(text)
            this.setSelection(this.text?.length ?: 0)
        }
    }

}