package com.example.productapp.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.productapp.Product
import com.example.productapp.core.AbstractVisibility
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ProductListUi(
    private val id: Int,
    private val items: List<Product>,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(RecyclerView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {
    private val cardUi = CardUi(items)

    fun assertRecipeListChanged() {
        interaction.check(matches(withId(id))) // todo solve it (потом)
    }

    fun assertFirstProductIsLiked() {
        cardUi.assertRecipeListChanged()
    }

    fun clickFirstProduct() {
        cardUi.click()
    }

    fun clickLikeOnProduct() {
        cardUi.clickLikeOnProduct()
    }
}