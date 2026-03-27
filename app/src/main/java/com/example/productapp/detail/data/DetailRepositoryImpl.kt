package com.example.productapp.detail.data

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.product.data.ProductData

class DetailRepositoryImpl(
    private val dao: ProductDao
) : DetailRepository {

    override suspend fun product(id: Int): ProductData {
        val cache = dao.getProductById(id)
        return cache.toData()
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return dao.isProductFavorite(id)
    }

    override suspend fun toggleFavorite(id: Int) {
        val currentStatus = dao.isProductFavorite(id)
        dao.updateFavoriteStatus(id, !currentStatus)
    }
}