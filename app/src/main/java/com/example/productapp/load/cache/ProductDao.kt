package com.example.productapp.load.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveProducts(products: List<ProductCache>)

    @Query("SELECT * FROM products_table")
    suspend fun allProducts(): List<ProductCache>

    @Query("SELECT * FROM products_table WHERE title LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductCache>

    @Query("SELECT * FROM products_table WHERE id = :id")
    suspend fun productById(id: Int): ProductCache

    @Query("SELECT * FROM products_table WHERE category = :category")
    suspend fun productsByCategory(category: String): List<ProductCache>

    @Query("SELECT * FROM products_table WHERE isFavorite = 1")
    suspend fun favoriteProducts(): List<ProductCache>

    @Query("UPDATE products_table SET isFavorite = :status WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, status: Boolean)

    @Query("SELECT isFavorite FROM products_table WHERE id = :id")
    suspend fun isProductFavorite(id: Int): Boolean
}