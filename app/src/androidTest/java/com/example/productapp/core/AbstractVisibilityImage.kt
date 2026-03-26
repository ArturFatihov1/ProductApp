package com.example.productapp.core

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.not

abstract class AbstractVisibilityImage(
    protected val interaction: ViewInteraction,
    protected val url: String
) {
    fun assertVisibleImage() {
        interaction.check(matches(ImageViewUrlMatcher(url))).check(matches(isDisplayed()))
    }

    fun assertNotVisibleImage() {
        interaction.check(matches(not(ImageViewUrlMatcher(url)))).check(matches(not(isDisplayed())))
    }
}