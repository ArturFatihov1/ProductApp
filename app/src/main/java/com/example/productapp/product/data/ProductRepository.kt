package com.example.productapp.product.data

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.load.cloud.CategoryCloud
import com.example.productapp.load.cloud.ProductService
import com.example.productapp.load.cloud.toCache

interface ProductRepository {

    suspend fun products(): List<ProductData>
    suspend fun search(query: String): List<ProductData>
    suspend fun toggleFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean
    suspend fun favoritesCount(): Int
    suspend fun getCategories(): List<CategoryCloud>
    suspend fun productsByCategory(category: String): List<ProductData>

    class Base(
        private val dao: ProductDao,
        private val service: ProductService
    ) : ProductRepository {

        override suspend fun products(): List<ProductData> {
            try {
                val response = service.loadProducts().execute().body()
                response?.products?.let { dao.saveProducts(it.map { cloud -> cloud.toCache() }) }
            } catch (e: Exception) {
            }
            return dao.allProducts().map { it.toData() }
        }

        override suspend fun search(query: String): List<ProductData> {
            try {
                val response = service.searchProducts(query).execute().body()
                response?.products?.let { dao.saveProducts(it.map { cloud -> cloud.toCache() }) }
            } catch (e: Exception) {
            }
            return dao.searchProducts(query).map { it.toData() }
        }

        override suspend fun productsByCategory(category: String): List<ProductData> {
            try {
                val response = service.getProductsByCategory(category).execute().body()
                response?.products?.let { dao.saveProducts(it.map { cloud -> cloud.toCache() }) }
            } catch (e: Exception) {
            }
            return dao.productsByCategory(category).map { it.toData() }
        }

        override suspend fun getCategories(): List<CategoryCloud> {
            return try {
                service.getCategories().execute().body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

        override suspend fun toggleFavorite(id: Int) {
            val current = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !current)
        }

        override suspend fun isFavorite(id: Int): Boolean = dao.isProductFavorite(id)

        override suspend fun favoritesCount(): Int = dao.favoriteProducts().size
    }
}