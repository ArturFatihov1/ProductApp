package com.example.productapp.load.data

interface LoadRepository {
    suspend fun load()
}

class NoInternetConnectionException : Exception()
class BackendException(message: String) : Exception(message)