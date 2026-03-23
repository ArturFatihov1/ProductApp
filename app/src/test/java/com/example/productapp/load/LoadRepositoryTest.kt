package com.example.productapp.load


import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class LoadRepositoryTest {

    private val gson = Gson()

    @Test
    fun test() {
        val url = "https://dummyjson.com/products"
        val connection = URL(url).openConnection() as HttpsURLConnection
        try {
            val data = connection.inputStream.bufferedReader().use { it.readText() }
            assertTrue(data.isNotEmpty())

            val response = gson.fromJson(data, Response::class.java)
            val list = response.results
            assertEquals(30, list.size)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
    }
}

private class Response(
    val results: List<Products>
)

private class Products()