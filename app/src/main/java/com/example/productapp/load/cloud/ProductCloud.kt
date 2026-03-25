package com.example.productapp.load.cloud

import com.example.productapp.load.cache.ProductCache
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

fun ProductCloud.toCache() = ProductCache(
    id = id,
    title = title,
    description = description,
    price = price,
    stock = stock,
    images = images.joinToString("|")
)