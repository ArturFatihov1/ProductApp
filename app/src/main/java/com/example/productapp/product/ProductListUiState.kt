package com.example.productapp.product

import com.example.productapp.views.text.UpdateText


interface ProductListUiState {

    fun update(
        searchInput: UpdateText,
        productList: UpdateProductList
    ) = Unit

    fun navigate(navigate: NavigateToFavorites) = Unit
    fun navigateToDetail(navigate: NavigateToDetail) = Unit

    data class Base(
        private val products: List<ProductItemUiState>
    ) : ProductListUiState {
        override fun update(searchInput: UpdateText, productList: UpdateProductList) {
            productList.update(products)
        }
    }

    object FavoriteLoad : ProductListUiState {
        override fun navigate(navigate: NavigateToFavorites) {
            navigate.navigateToFavorites()
        }
    }

    data class DetailLoad(
        val productId: Int
    ) : ProductListUiState {
        override fun navigateToDetail(navigate: NavigateToDetail) {
            navigate.navigateToDetail(productId)
        }
    }
}