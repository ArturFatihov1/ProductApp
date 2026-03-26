package com.example.productapp.load.presentation

import com.example.productapp.R
import com.example.productapp.product.presentation.NavigateToProductList
import com.example.productapp.views.error.ErrorUiState
import com.example.productapp.views.error.UpdateError
import com.example.productapp.views.visibility.UpdateVisibility
import com.example.productapp.views.visibility.VisibilityUiState

interface LoadUiState {
    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigate: NavigateToProductList) = Unit

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryUiState: VisibilityUiState,
        private val progressUiState: VisibilityUiState
    ) : LoadUiState {
        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) {
            errorTextView.update(errorUiState)
            retryButton.update(retryUiState)
            progressBar.update(progressUiState)
        }
    }

    object Progress : Abstract(
        ErrorUiState.Hide,
        VisibilityUiState.Gone,
        VisibilityUiState.Visible
    )

    object Success : Abstract(
        ErrorUiState.Hide,
        VisibilityUiState.Gone,
        VisibilityUiState.Gone
    ) {
        override fun navigate(navigate: NavigateToProductList) = navigate.navigateToProductList()
    }

    data class ErrorRes(val messageId: Int = R.string.no_internet_connection) : Abstract(
        ErrorUiState.ShowRes(messageId),
        VisibilityUiState.Visible,
        VisibilityUiState.Gone,
    )

    data class Error(private val message: String) : Abstract(
        ErrorUiState.Show(message),
        VisibilityUiState.Visible,
        VisibilityUiState.Gone,
    )

}