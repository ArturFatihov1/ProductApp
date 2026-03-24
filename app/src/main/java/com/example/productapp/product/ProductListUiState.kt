package com.example.productapp.product

import com.example.productapp.views.text.UpdateText

interface ProductListUiState {
    fun update(searchInput: UpdateText, productList: UpdateProductList, favCountView: UpdateText)

    data class Base(
        val products: List<ProductItemUiState>,
        val favoriteCount: Int,
        val query: String
    ) : ProductListUiState {
        override fun update(
            searchInput: UpdateText,
            productList: UpdateProductList,
            favCountView: UpdateText
        ) {
            productList.update(products)
            favCountView.update(if (favoriteCount > 0) favoriteCount.toString() else "")
            searchInput.update(query)
        }
    }

    object Empty : ProductListUiState {
        override fun update(
            searchInput: UpdateText,
            productList: UpdateProductList,
            favCountView: UpdateText
        ) {
            productList.update(emptyList())
            favCountView.update("")
            searchInput.update("")
        }
    }
}