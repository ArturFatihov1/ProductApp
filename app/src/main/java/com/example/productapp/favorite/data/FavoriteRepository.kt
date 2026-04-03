package com.example.productapp.favorite.data

import com.example.productapp.product.data.ProductData

interface FavoriteRepository {
    suspend fun favorites(): List<ProductData>
    suspend fun toggleFavorite(id: Int)
}