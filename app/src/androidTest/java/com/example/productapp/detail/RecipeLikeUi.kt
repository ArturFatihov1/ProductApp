package com.example.productapp.detail

import android.view.View
import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.productapp.R
import com.example.productapp.core.AbstractVisibility
import com.example.productapp.core.hasDrawable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class RecipeLikeUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ImageButton::class.java),
            withParent(withId(R.id.detailHeader)),
            classTypeMatcher
        )
    )
) {
    fun click() {
        interaction.perform(ViewActions.click())
    }

    fun assertIsLiked() {
        interaction.check(matches(hasDrawable(R.drawable.ic_like_selected)))
    }

    fun assertIsNotLiked() {
        interaction.check(matches(hasDrawable(R.drawable.ic_like_unselected)))
    }
}
