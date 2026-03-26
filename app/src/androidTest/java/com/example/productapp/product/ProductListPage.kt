package com.example.productapp.product

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.productapp.Product
import com.example.productapp.R
import com.example.productapp.core.ButtonUi

class ProductListPage(private val products: List<Product> = emptyList()) {

    private val favoriteButton = ButtonUi(R.id.favoriteButton)
    private val searchInput = onView(withId(R.id.searchInput))
    private val productListUi = ProductListUi(R.id.productList, products)

    private val progressBar = onView(withId(R.id.progressBar))
    private val errorText = onView(withId(R.id.errorText))
    private val retryButton = onView(withId(R.id.retryButton))

    fun assertLoadingState() {
        progressBar.check(matches(isDisplayed()))
    }

    fun assertErrorState() {
        errorText.check(matches(isDisplayed()))
        retryButton.check(matches(isDisplayed()))
    }

    fun assertProductListState() {
        favoriteButton.assertVisible()
        searchInput.check(matches(isDisplayed()))
        productListUi.assertVisible()
    }

    fun clickFavoriteButton() = favoriteButton.click()
    fun clickRetry() = retryButton.perform(click())
    fun clickFirstProduct() = productListUi.clickFirstProduct()
    fun clickLikeOnProduct(position: Int = 0) = productListUi.clickLikeOnProduct(position)

    fun addInput(text: String) {
        searchInput.perform(typeText(text))
    }

    fun assertInputEmptyState() {
        searchInput.check(matches(withText("")))
    }

    fun assertFirstProductIsLiked() {
        productListUi.assertFirstProductIsLiked()
    }

    fun waitTillError() {
        // уже реализовано в ErrorUi
        onView(isRoot()).perform(
            com.example.productapp.core.waitTillDoesntExist(
                R.id.errorText,
                2000
            )
        )
    }
}