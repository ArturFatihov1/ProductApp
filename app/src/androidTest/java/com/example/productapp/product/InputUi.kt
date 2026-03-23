package com.example.productapp.product

import android.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.productapp.R
import com.example.productapp.core.AbstractVisibility
import org.hamcrest.CoreMatchers.allOf

class InputUi : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(R.id.searchInput),
            isAssignableFrom(SearchView::class.java),
        )
    )
) {
    fun addInput(text: String) {}
    fun assertInitialState() {
        TODO("Not yet implemented")
    }

    fun assertInputSufficientState() {
        TODO("Not yet implemented")
    }

}