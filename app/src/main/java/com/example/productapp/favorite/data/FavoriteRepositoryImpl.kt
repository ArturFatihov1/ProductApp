package com.example.productapp.favorite.data

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cache.toData
import com.example.productapp.product.data.ProductData

class FavoriteRepositoryImpl(private val dao: ProductDao) : FavoriteRepository {

    override suspend fun favorites(): List<ProductData> {
        return dao.favoriteProducts().map { it.toData() }
    }

    override suspend fun toggleFavorite(id: Int) {
        val current = dao.isProductFavorite(id)
        dao.updateFavoriteStatus(id, !current)
    }
}
