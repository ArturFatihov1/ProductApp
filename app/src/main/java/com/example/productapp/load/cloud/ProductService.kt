package com.example.productapp.load.cloud

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun loadProducts(): Response<ProductResponse>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductResponse>

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<ProductResponse>
}