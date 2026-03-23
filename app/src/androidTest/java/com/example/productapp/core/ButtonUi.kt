package com.example.productapp.core

import android.view.View
import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ButtonUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ImageButton::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {
    fun click() {
        interaction.perform(ViewActions.click())
    }
}