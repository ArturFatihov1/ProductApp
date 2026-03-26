package com.example.productapp.detail

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.productapp.Product
import com.example.productapp.R
import com.example.productapp.core.ButtonUi
import org.hamcrest.Matcher

class DetailPage(private val product: Product) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.detailLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(android.widget.LinearLayout::class.java))

    private val backButton = ButtonUi(
        id = R.id.backButton,
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
        id = R.id.title,
        text = product.title,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val descriptionUi = TextUi(
        id = R.id.description,
        text = product.description,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun assertDetailState() {
        backButton.assertVisible()
        recipeLikeUi.assertVisible()
        imageUi.assertVisibleImage()
        titleUi.assertVisible()
        descriptionUi.assertVisible()
    }

    fun clickBack() = backButton.click()
    fun clickOnLike() = recipeLikeUi.click()

    fun assertProductIsLiked() = recipeLikeUi.assertIsLiked()
    fun assertProductIsNotLiked() = recipeLikeUi.assertIsNotLiked()
}