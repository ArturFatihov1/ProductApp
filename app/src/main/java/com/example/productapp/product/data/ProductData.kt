package com.example.productapp.product.data

data class ProductData(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val stock: Int,
    val images: List<String>
)