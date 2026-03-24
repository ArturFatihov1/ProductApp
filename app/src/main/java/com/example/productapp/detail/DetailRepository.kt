package com.example.productapp.detail

import com.example.productapp.core.ProductData


interface DetailRepository {
    suspend fun product(id: Int): ProductData
    suspend fun isFavorite(id: Int): Boolean
    suspend fun toggleFavorite(id: Int)

    class Base(
        private val dao: ProductDao
    ) : DetailRepository {

        override suspend fun product(id: Int): ProductData {
            return dao.productById(id).toData()
        }

        override suspend fun isFavorite(id: Int): Boolean {
            return dao.isProductFavorite(id)
        }

        override suspend fun toggleFavorite(id: Int) {
            val current = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !current)
        }
    }
}