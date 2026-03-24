package com.example.productapp.product

import com.example.productapp.core.ProductData

interface ProductRepository {
    suspend fun products(query: String = ""): List<ProductData>
    suspend fun toggleFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean

    class Base(
        private val dao: ProductDao
    ) : ProductRepository {

        override suspend fun products(query: String): List<ProductData> {
            val cacheList = if (query.isEmpty()) {
                dao.allProducts()
            } else {
                dao.searchProducts(query)
            }
            return cacheList.map { it.toData() }
        }

        override suspend fun toggleFavorite(id: Int) {
            val current = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !current)
        }

        override suspend fun isFavorite(id: Int): Boolean {
            return dao.isProductFavorite(id)
        }
    }
}