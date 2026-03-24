package com.example.productapp.product

interface UpdateProductList {
    fun update(newList: List<ProductItemUiState>)
}

interface UpdateLikeIcon {
    fun update(isLiked: Boolean)
}