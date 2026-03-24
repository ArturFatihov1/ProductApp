package com.example.productapp.load.cloud

import com.google.gson.annotations.SerializedName

data class ProductCloud(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("images")
    val images: List<String>
)