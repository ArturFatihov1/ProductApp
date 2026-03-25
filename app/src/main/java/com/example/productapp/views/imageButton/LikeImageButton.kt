package com.example.productapp.views.imageButton

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.example.productapp.R

class LikeImageButton : AppCompatImageButton, UpdateLikeIcon {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun update(isFavorite: Boolean) {
        val iconRes = if (isFavorite)
            R.drawable.ic_like_selected
        else
            R.drawable.ic_like_unselected
        setImageResource(iconRes)

    }
}

interface UpdateLikeIcon {
    fun update(isFavorite: Boolean)
}