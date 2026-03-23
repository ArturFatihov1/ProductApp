package com.example.productapp.product

import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.example.productapp.Product
import com.example.productapp.core.AbstractVisibility
import org.hamcrest.CoreMatchers.allOf

class CardUi(private val items: List<Product>) :
    AbstractVisibility(
        interaction = onView(
            allOf(
                isAssignableFrom(CardView::class.java),
            )
        )
    ) {


    fun click() {
        interaction.perform(ViewActions.click())
    }

    fun assertRecipeListChanged() {
        //todo create
    }

    fun clickLikeOnProduct() {
        //todo create
    }

}
