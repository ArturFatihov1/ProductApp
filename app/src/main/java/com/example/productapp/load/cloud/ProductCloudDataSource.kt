package com.example.productapp.load.cloud

interface ProductCloudDataSource {
    suspend fun load(): List<ProductDTO>
}