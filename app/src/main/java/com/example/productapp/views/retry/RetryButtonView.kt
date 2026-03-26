package com.example.productapp.views.retry

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.productapp.views.visibility.UpdateVisibility
import com.example.productapp.views.visibility.VisibilityUiState

class RetryButtonView : AppCompatButton, UpdateVisibility {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun update(visibility: Int) {
        this.visibility = visibility
    }

    override fun update(state: VisibilityUiState) {
        state.update(this)
    }
}