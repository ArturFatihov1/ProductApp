package com.example.productapp.detail.data

import com.example.productapp.product.data.ProductData

interface DetailRepository {
    suspend fun product(): ProductData
    suspend fun isFavorite(): Boolean
    suspend fun toggleFavorite()
    fun saveId(id: Int)
}