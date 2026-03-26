package com.example.productapp.detail.data

import com.example.productapp.core.IntCache
import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.product.data.ProductData

interface DetailRepository {
    suspend fun product(): ProductData
    suspend fun isFavorite(): Boolean
    suspend fun toggleFavorite()
    fun saveId(id: Int)

    class Base(
        private val dao: ProductDao,
        private val productIdCache: IntCache
    ) : DetailRepository {

        override fun saveId(id: Int) = productIdCache.save(id)

        override suspend fun product(): ProductData {
            val id = productIdCache.read()
            return dao.productById(id).toData()
        }

        override suspend fun isFavorite(): Boolean {
            return dao.isProductFavorite(productIdCache.read())
        }

        override suspend fun toggleFavorite() {
            val id = productIdCache.read()
            val current = dao.isProductFavorite(id)
            dao.updateFavoriteStatus(id, !current)
        }
    }
}