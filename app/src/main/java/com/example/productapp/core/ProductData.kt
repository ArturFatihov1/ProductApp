package com.example.productapp.core

data class ProductData(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val images: List<String>
)