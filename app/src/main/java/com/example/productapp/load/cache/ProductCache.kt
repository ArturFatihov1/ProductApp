package com.example.productapp.load.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class ProductCache(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("stock")
    val stock: Int,
    @ColumnInfo("images")
    val images: String,
    @ColumnInfo("isFavorite")
    val isFavorite: Boolean = false
)