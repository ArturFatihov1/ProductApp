package com.example.productapp.detail

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.productapp.Product
import com.example.productapp.R
import org.hamcrest.Matcher

class DetailPage(product: Product) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.detailLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val backButton = ButtonUi(
        id = R.id.backButton,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val recipeLikeUi = RecipeLikeUi(
        id = R.id.recipeLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val imageUi = ImageUI(
        id = R.id.imageDetail,
        url = product.url,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val titleUi = TextUi(
        R.id.title,
        text = product.title,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val description = TextUi(
        R.id.description,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun assertDetailState() {
        backButton.assertVisible()
        recipeLikeUi.assertVisible()
        imageUi.assertVisible()
        titleUi.assertVisible()
        description.assertVisible()
    }

    fun clickBack() {
        backButton.click()
    }

    fun clickOnLike() {
        recipeLikeUi.click()
    }

    fun assertProductIsLiked() {
        recipeLikeUi.assertIsLiked()
    }

    fun assertProductsIsNotLiked() {
        recipeLikeUi.assertIsNotLiked()
    }

}