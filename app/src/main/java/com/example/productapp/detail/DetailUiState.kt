package com.example.productapp.detail

import com.example.productapp.views.image.UpdateImages
import com.example.productapp.views.imageButton.UpdateLikeIcon
import com.example.productapp.views.text.UpdateText


interface DetailUiState {
    fun show(
        titleView: UpdateText,
        descriptionView: UpdateText,
        priceView: UpdateText,
        stockStatusView: UpdateText,
        imagesView: UpdateImages,
        likeIcon: UpdateLikeIcon
    )

    data class Base(
        val id: Int,
        val title: String,
        val description: String,
        val price: Double,
        val stock: Int,
        val images: List<String>,
        val isFavorite: Boolean
    ) : DetailUiState {
        override fun show(
            titleView: UpdateText,
            descriptionView: UpdateText,
            priceView: UpdateText,
            stockStatusView: UpdateText,
            imagesView: UpdateImages,
            likeIcon: UpdateLikeIcon
        ) {
            titleView.update(title)
            descriptionView.update(description)
            priceView.update("$price $")
            val stockText = if (stock > 0) "В наличии" else "Нет в наличии"
            stockStatusView.update(stockText)
            imagesView.update(images)
            likeIcon.update(isFavorite)
        }
    }

    object Empty : DetailUiState {
        override fun show(
            titleView: UpdateText,
            descriptionView: UpdateText,
            priceView: UpdateText,
            stockStatusView: UpdateText,
            imagesView: UpdateImages, likeIcon: UpdateLikeIcon
        ) = Unit
    }
}
