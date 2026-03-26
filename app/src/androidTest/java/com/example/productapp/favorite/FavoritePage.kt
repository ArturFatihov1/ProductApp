package com.example.productapp.favorite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.productapp.Product
import com.example.productapp.R
import com.example.productapp.core.ButtonUi
import com.example.productapp.product.ProductListUi

class FavoritePage(private val products: List<Product> = emptyList()) {

    private val backButton = ButtonUi(R.id.backButton)
    private val productListUi = ProductListUi(R.id.productList, products)

    fun assertFavoritesState() {
        backButton.assertVisible()
        onView(withText(R.string.headerFavorite)).check(matches(isDisplayed()))
        productListUi.assertVisible()
        onView(withId(R.id.emptyText)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    fun assertFavoritesEmptyState() {
        backButton.assertVisible()
        onView(withText(R.string.headerFavorite)).check(matches(isDisplayed()))
        productListUi.assertNotVisible()
        onView(withId(R.id.emptyText)).check(matches(isDisplayed()))
    }

    fun clickFirstRecipe() = productListUi.clickFirstProduct()
}