package com.example.productapp.load.cache

import android.content.Context
import androidx.room.Room
import com.example.productapp.R

interface CacheModule {
    fun dao(): ProductDao

    class Base(applicationContext: Context) : CacheModule {
        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                ProductDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }

        override fun dao(): ProductDao = database.dao()
    }
}