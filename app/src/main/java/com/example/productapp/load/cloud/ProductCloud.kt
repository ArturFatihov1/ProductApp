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
    @SerializedName("category")
    val category: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("images")
    val images: List<String>
)

data class CategoryCloud(
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String
)

fun ProductCloud.toCache() = ProductCache(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    stock = stock,
    images = images.joinToString("|")
)