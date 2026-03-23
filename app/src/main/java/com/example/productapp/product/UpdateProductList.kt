package com.example.productapp.product

interface UpdateProductList {
    fun update(newList: List<ProductItemUiState>)
}

interface UpdateText {
    fun update(text: String)
}

interface UpdateLikeIcon {
    fun update(isLiked: Boolean)
}