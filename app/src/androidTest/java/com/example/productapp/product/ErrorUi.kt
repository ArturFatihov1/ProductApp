package com.example.productapp.product

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.productapp.core.AbstractVisibility
import com.example.productapp.core.waitTillDoesntExist
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ErrorUi(
    private val id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(TextView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {

    fun waitTillError() {
        onView(isRoot()).perform(waitTillDoesntExist(id, 1500))
    }
}