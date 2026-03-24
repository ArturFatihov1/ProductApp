package com.example.productapp.favorite

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.product.ProductData

interface FavoriteRepository {
    suspend fun favorites(): List<ProductData>
    suspend fun toggleFavorite(id: Int)

    class Base(
        private val dao: ProductDao
    ) : FavoriteRepository {

        override suspend fun favorites(): List<ProductData> {
            return dao.favoriteProducts().map { it.toData() }
        }

        override suspend fun toggleFavorite(id: Int) {
            val current = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !current)
        }
    }
}