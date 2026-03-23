package com.example.productapp.favorite

import com.example.productapp.product.ProductItemUiState
import com.example.productapp.product.UpdateProductList

interface FavoriteUiState {
    fun show(
        productList: UpdateProductList,
        emptyStateView: UpdateVisibility
    )

    data class Base(
        private val favorites: List<ProductItemUiState>
    ) : FavoriteUiState {
        override fun show(productList: UpdateProductList, emptyStateView: UpdateVisibility) {
            productList.update(favorites)
            val visibility =
                if (favorites.isEmpty()) VisibilityUiState.Visible else VisibilityUiState.Gone
            emptyStateView.update(visibility)
        }
    }
}