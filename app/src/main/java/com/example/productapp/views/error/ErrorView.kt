package com.example.productapp.views.error

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import java.io.Serializable

class ErrorView : AppCompatTextView, UpdateError {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var state: ErrorUiState

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = ErrorSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ErrorSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: ErrorUiState) {
        state = uiState
        state.update(this)
    }

    override fun updateText(textResId: Int) {
        setText(textResId)
    }

    override fun updateText(text: String) {
        setText(text)
    }

    override fun updateVisibility(visibility: Int) {
        this.visibility = visibility
    }


}

interface UpdateError {
    fun update(uiState: ErrorUiState)
    fun updateText(textResId: Int)
    fun updateText(text: String)
    fun updateVisibility(visibility: Int)
}

interface ErrorUiState : Serializable {

    fun update(updateError: UpdateError)

    abstract class Abstract(private val visibility: Int) : ErrorUiState {
        override fun update(updateError: UpdateError) {
            updateError.updateVisibility(visibility)
        }
    }

    object Hide : Abstract(View.GONE)

    data class ShowRes(private val resId: Int) : Abstract(View.VISIBLE) {
        override fun update(updateError: UpdateError) {
            super.update(updateError)
            updateError.updateText(resId)
        }
    }

    data class Show(private val message: String) : Abstract(View.VISIBLE) {

        override fun update(updateError: UpdateError) {
            super.update(updateError)
            updateError.updateText(message)
        }
    }
}
