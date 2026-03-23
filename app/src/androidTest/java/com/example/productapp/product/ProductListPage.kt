package com.example.productapp.product

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.productapp.Product
import com.example.productapp.R
import com.example.productapp.core.ButtonUi
import org.hamcrest.Matcher

class ProductListPage(products: List<Product>) {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.productListLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val favoriteButtonUi = ButtonUi(
        id = R.id.productLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val retryButtonUi = RetryButtonUi(
        id = R.id.retryButton,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val searchFieldUi = InputUi()

    private val productListUi = ProductListUi(
        id = R.id.productList,
        items = products,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val loadingUi = LoadingUi(
        id = R.id.progressBar,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val errorUi = ErrorUi(
        id = R.id.errorText,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )


    fun assertProductListState() {
        favoriteButtonUi.assertVisible()
        searchFieldUi.assertVisible()
        productListUi.assertVisible()
    }


    fun clickFavoriteButton() {
        favoriteButtonUi.click()
    }

    fun assertRecipeListChanged() {
        productListUi.assertRecipeListChanged()
    }


    fun assertLoadingState() {
        loadingUi.assertVisible()
        errorUi.assertNotVisible()
        retryButtonUi.assertNotVisible()
    }

    fun assertErrorState() {
        loadingUi.assertNotVisible()
        errorUi.assertVisible()
        retryButtonUi.assertVisible()
    }

    fun addInput(text: String) {
        searchFieldUi.addInput(text)
    }

    fun assertInputEmptyState() {
        searchFieldUi.assertInitialState()
    }

    fun waitTillError() {
        errorUi.waitTillError()
    }

    fun clickRetry() {
        retryButtonUi.click()
    }

    fun assertInputSufficientState() {
        searchFieldUi.assertInputSufficientState()
    }

    fun assertFirstProductIsLiked() {
        productListUi.assertFirstProductIsLiked()
    }

    fun clickFirstProduct() {
        productListUi.clickFirstProduct()
    }

    fun clickLikeOnProduct(i: Int) {
        productListUi.clickLikeOnProduct()
    }
}