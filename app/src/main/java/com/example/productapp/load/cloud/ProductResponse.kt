package com.example.productapp.load.cloud

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<ProductCloud>,
    @SerializedName("total")
    val total: Int
)

