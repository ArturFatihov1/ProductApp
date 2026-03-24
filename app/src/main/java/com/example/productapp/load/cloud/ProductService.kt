package com.example.productapp.load.cloud

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    fun loadProducts(): Call<ProductResponse>
}