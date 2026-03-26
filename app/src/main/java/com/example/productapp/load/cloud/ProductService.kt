package com.example.productapp.load.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    fun loadProducts(): Call<ProductResponse>

    @GET("products/search")
    fun searchProducts(@Query("q") query: String): Call<ProductResponse>

    @GET("products/categories")
    fun getCategories(): Call<List<CategoryCloud>>

    @GET("products/category/{category}")
    fun getProductsByCategory(@Path("category") category: String): Call<ProductResponse>
}