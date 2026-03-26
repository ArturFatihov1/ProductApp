package com.example.productapp.product

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.productapp.Product
import com.example.productapp.R
import com.example.productapp.core.AbstractVisibility
import com.example.productapp.core.hasDrawable
import org.hamcrest.CoreMatchers.allOf

class ProductListUi(
    private val recyclerId: Int,
    private val items: List<Product>
) : AbstractVisibility(onView(withId(recyclerId))) {

    fun clickFirstProduct() {
        onView(allOf(withId(R.id.itemView), isDisplayed())).perform(click())
    }

    fun clickLikeOnProduct(position: Int = 0) {
        onView(allOf(withId(R.id.likeButton), isDisplayed())).perform(click())
    }

    fun assertFirstProductIsLiked() {
        onView(allOf(withId(R.id.likeButton), isDisplayed()))
            .check(
                matches(
                    hasDrawable(R.drawable.ic_like_selected)
                )
            )
    }
}