package com.example.productapp.detail.data

import com.example.productapp.product.data.ProductData

interface DetailRepository {
    suspend fun product(id: Int): ProductData
    suspend fun isFavorite(id: Int): Boolean
    suspend fun toggleFavorite(id: Int)
}