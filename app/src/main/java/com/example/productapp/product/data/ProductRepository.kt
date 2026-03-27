package com.example.productapp.product.data

import com.example.productapp.load.cloud.CategoryDTO

interface ProductRepository {
    suspend fun products(): List<ProductData>
    suspend fun search(query: String): List<ProductData>
    suspend fun toggleFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean
    suspend fun favoritesCount(): Int
    suspend fun getCategories(): List<CategoryDTO>
    suspend fun productsByCategory(category: String): List<ProductData>
}