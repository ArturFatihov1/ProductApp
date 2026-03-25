package com.example.productapp.favorite

import com.example.productapp.detail.presentation.NavigateToDetail
import com.example.productapp.product.ProductItemUiState
import com.example.productapp.product.UpdateProductList
import com.example.productapp.views.visibility.VisibilityText
import com.example.productapp.views.visibility.VisibilityUiState

interface FavoriteUiState {
    fun show(
        productList: UpdateProductList,
        emptyStateView: VisibilityText
    ) = Unit

    fun navigateToDetail(navigate: NavigateToDetail) = Unit

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

    data class DetailLoad(private val productId: Int) : FavoriteUiState {
        override fun navigateToDetail(navigate: NavigateToDetail) {
            navigate.navigateToDetail(productId)
        }
    }
}