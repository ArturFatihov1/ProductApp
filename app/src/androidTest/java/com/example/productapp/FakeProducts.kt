package com.example.productapp

internal object FakeProducts {

    private val products = listOf<Product>(
        Product(
            id = "1",
            title = "Essence Mascara Lash Princess",
            url = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
            price = 9.99,
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula."
        ),
        Product(
            id = "2",
            title = "Eyeshadow Palette with Mirror",
            url = "https://www.themealdb.com/images/media/meals/0s80wo1764374393.jpg",
            price = 19.99,
            description = "The Eyeshadow Palette with Mirror offers a versatile range of eyeshadow shades for creating stunning eye looks. With a built-in mirror, it's convenient for on-the-go makeup application.",
        ),
    )
    val allProducts = products
    val firstProduct = products.first()

    fun searchedProducts(query: String) = products.filter { product ->
        product.title.contains(query, ignoreCase = true)
    }
}