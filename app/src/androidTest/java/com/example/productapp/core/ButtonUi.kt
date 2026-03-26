package com.example.productapp.core

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId

open class ButtonUi(private val id: Int) : AbstractVisibility(onView(withId(id))) {

    fun click() {
        interaction.perform(
            androidx.test.espresso.action.ViewActions.click()
        )
    }


}