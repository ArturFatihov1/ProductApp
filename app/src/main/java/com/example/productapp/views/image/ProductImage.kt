package com.example.productapp.views.image

import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import coil.load

class ProductImage : AppCompatImageView, UpdateImages {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun update(url: List<String>) {
        if (url.isNotEmpty()) {
            this.load(url[0]) {
                crossfade(true)
                placeholder(R.drawable.ic_menu_gallery)
            }
        }
    }

}

interface UpdateImages {
    fun update(url: List<String>)
}