package com.example.productapp.load


import java.io.IOException

interface LoadRepository {
    suspend fun load()

    class Base(
        private val cloudDataSource: ProductCloudDataSource,
        private val cacheDataSource: ProductDao
    ) : LoadRepository {

        override suspend fun load() {
            try {
                val products = cloudDataSource.loadProducts()
                cacheDataSource.saveProducts(products.map { it.toCache() })
            } catch (e: Exception) {
                if (e is IOException) throw NoInternetConnectionException()
                throw BackendException(e.message ?: "Unknown error")
            }
        }
    }
}

class NoInternetConnectionException : Exception()
class BackendException(message: String) : Exception(message)