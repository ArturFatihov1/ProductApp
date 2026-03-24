package com.example.productapp.product

data class ProductUiState(
    val id: Int,
    val title: String,
    val price: Double,
    val isFavorite: Boolean,
    val onClick: (Int) -> Unit,
    val onFavoriteClick: (Int) -> Unit
)