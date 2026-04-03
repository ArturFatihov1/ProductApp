package com.example.productapp.favorite.presentation

import com.example.productapp.product.presentation.ProductItemUiState
import com.example.productapp.product.presentation.UpdateProductList
import com.example.productapp.views.visibility.VisibilityText
import com.example.productapp.views.visibility.VisibilityUiState

sealed interface FavoriteUiState {
    fun show(
        productList: UpdateProductList,
        emptyStateView: VisibilityText
    ) = Unit

    data class Base(
        val favorites: List<ProductItemUiState>
    ) : FavoriteUiState {
        override fun show(productList: UpdateProductList, emptyStateView: VisibilityText) {
            productList.update(favorites)
            val visibility =
                if (favorites.isEmpty()) VisibilityUiState.Visible else VisibilityUiState.Gone
            emptyStateView.update(visibility)
        }
    }
}