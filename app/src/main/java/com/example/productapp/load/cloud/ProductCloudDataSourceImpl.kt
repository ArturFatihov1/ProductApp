package com.example.productapp.load.cloud

class ProductCloudDataSourceImpl(private val service: ProductService) : ProductCloudDataSource {
    override suspend fun load(): List<ProductDTO> {
        val result = service.loadProducts()
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