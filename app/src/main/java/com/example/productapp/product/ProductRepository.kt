package com.example.productapp.product

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData

interface ProductRepository {
    suspend fun products(query: String = ""): List<ProductData>
    suspend fun toggleFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean
    suspend fun favoritesCount(): Int

    class Base(private val dao: ProductDao) : ProductRepository {

        override suspend fun products(query: String): List<ProductData> {
            val cacheList = if (query.isEmpty())
                dao.allProducts()
            else
                dao.searchProducts(query)
            return cacheList.map { it.toData() }
        }

        override suspend fun toggleFavorite(id: Int) {
            val isCurrentlyFavorite = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !isCurrentlyFavorite)
        }

        override suspend fun isFavorite(id: Int): Boolean = dao.isProductFavorite(id)

        override suspend fun favoritesCount(): Int = dao.favoriteProducts().size
    }
}