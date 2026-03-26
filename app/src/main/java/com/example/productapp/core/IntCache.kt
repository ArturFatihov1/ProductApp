package com.example.productapp.core

import android.content.SharedPreferences

interface IntCache {
    fun read(): Int
    fun save(newValue: Int)

    class Base(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Int = -1
    ) : IntCache {
        override fun read() = sharedPreferences.getInt(key, defaultValue)
        override fun save(newValue: Int) = sharedPreferences.edit().putInt(key, newValue).apply()
    }
}