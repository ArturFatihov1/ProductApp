package com.example.productapp.load.data


import com.example.productapp.load.cache.ProductDao
import com.example.productapp.load.cloud.ProductCloudDataSource
import com.example.productapp.load.cloud.toCache
import java.io.IOException

interface LoadRepository {
    suspend fun load()

    class Base(
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
}

class NoInternetConnectionException : Exception()
class BackendException(message: String) : Exception(message)