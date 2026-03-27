package com.example.productapp.load.data

import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cloud.ProductCloudDataSource
import com.example.productapp.load.cloud.toCache
import java.io.IOException

class LoadRepositoryImpl(
    private val cloudDataSource: ProductCloudDataSource,
    private val cacheDataSource: ProductDao
) : LoadRepository {

    override suspend fun load() {
        try {
            val cloudData = cloudDataSource.load()
            val cacheData = cloudData.map { it.toCache() }
            cacheDataSource.saveProducts(cacheData)
        } catch (e: Exception) {
            if (e is IOException) throw NoInternetConnectionException()
            throw BackendException(e.message ?: "Unknown error")
        }
    }
}