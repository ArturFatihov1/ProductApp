package com.example.productapp.load.cloud

interface ProductCloudDataSource {
    suspend fun load(): List<ProductCloud>

    class Base(private val service: ProductService) : ProductCloudDataSource {
        override suspend fun load(): List<ProductCloud> {
            val result = service.loadProducts().execute()
            if (result.isSuccessful) {
                val body = result.body()!!
                val list = body.products
                if (list.isEmpty()) {
                    throw IllegalStateException("service unavailable")
                } else {
                    return list
                }
            } else {
                throw IllegalStateException("Response error: ${result.code()}")
            }
        }
    }
}