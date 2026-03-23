package com.example.productapp.favorite

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.productapp.Product
import com.example.productapp.R
import org.hamcrest.Matcher

class FavoritePage(products: List<Product>) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.productListLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val backButton = ButtonUi(
        id = R.id.backButton,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val headerUi = TextUi(
        R.id.header,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val emptyFavorite = TextUi(
        id = R.id.emptyText,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val productListUi = ProductListUi(
        id = R.id.productList,
        items = products,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )


    fun assertFavoritesState() {
        backButton.assertVisible()
        headerUi.assertVisible()
        productListUi.assertVisible()
        emptyFavorite.assertNotVisible()
    }

    fun clickFirstRecipe() {
        productListUi.click()
    }

    fun assertFavoritesEmptyState() {
        backButton.assertVisible()
        headerUi.assertVisible()
        productListUi.assertNotVisible()
        emptyFavorite.assertVisible()
    }

}