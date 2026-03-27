package com.example.productapp.product.data

import android.util.Log
import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.load.cloud.CategoryDTO
import com.example.productapp.load.cloud.ProductDTO
import com.example.productapp.load.cloud.ProductService
import com.example.productapp.load.cloud.toCache

class ProductRepositoryImpl(
    private val dao: ProductDao,
    private val service: ProductService
) : ProductRepository {

    override suspend fun products(): List<ProductData> {
        try {
            val response = service.loadProducts()
            if (response.isSuccessful) {
                response.body()?.products?.let { list ->
                    val cacheList = list.map { dto: ProductDTO -> dto.toCache() }
                    dao.saveProducts(cacheList)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error loading products", e)
        }
        return dao.allProducts().map { it.toData() }
    }

    override suspend fun search(query: String): List<ProductData> {
        try {
            val response = service.searchProducts(query)
            if (response.isSuccessful) {
                response.body()?.products?.let { list ->
                    val cacheList = list.map { dto: ProductDTO -> dto.toCache() }
                    dao.saveProducts(cacheList)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Search error for query: $query", e)
        }
        return dao.searchProducts(query).map { it.toData() }
    }

    override suspend fun productsByCategory(category: String): List<ProductData> {
        try {
            val response = service.getProductsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.products?.let { list ->
                    val cacheList = list.map { dto: ProductDTO -> dto.toCache() }
                    dao.saveProducts(cacheList)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Category error: $category", e)
        }
        return dao.productsByCategory(category).map { it.toData() }
    }

    override suspend fun getCategories(): List<CategoryDTO> {
        return try {
            service.getCategories()
        } catch (e: Exception) {
            Log.e("ProductRepository", "Categories fetch error", e)
            emptyList()
        }
    }

    override suspend fun toggleFavorite(id: Int) {
        val current = dao.isProductFavorite(id)
        dao.updateFavoriteStatus(id, !current)
    }

    override suspend fun isFavorite(id: Int): Boolean = dao.isProductFavorite(id)

    override suspend fun favoritesCount(): Int {
        return dao.allProducts().count { it.isFavorite }
    }
}