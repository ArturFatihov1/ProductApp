package com.example.productapp.load.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductCache::class], version = 1)
abstract class ProductDatabase : RoomDatabase(), ClearDatabase {
    abstract fun dao(): ProductDao
    override suspend fun clear() = clearAllTables()
}

interface ClearDatabase {
    suspend fun clear()
}