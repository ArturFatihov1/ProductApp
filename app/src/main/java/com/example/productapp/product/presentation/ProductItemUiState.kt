package com.example.productapp.product.presentation

import com.example.productapp.views.image.UpdateImages
import com.example.productapp.views.imageButton.UpdateLikeIcon
import com.example.productapp.views.text.UpdateText

interface ProductItemUiState {

    val id: Int

    fun changeLike(): ProductItemUiState
    fun showImage(imageView: UpdateImages)
    fun isSame(other: ProductItemUiState): Boolean
    fun isContentSame(other: ProductItemUiState): Boolean

    fun show(
        titleView: UpdateText,
        priceView: UpdateText,
        likeIcon: UpdateLikeIcon
    )

    data class Base(
        override val id: Int,
        private val title: String,
        private val price: String,
        private val images: List<String>
    ) : ProductItemUiState {

        override fun show(titleView: UpdateText, priceView: UpdateText, likeIcon: UpdateLikeIcon) {
            titleView.update(title)
            priceView.update("$price $")
            likeIcon.update(false)
        }

        override fun changeLike() = Liked(id, title, price, images)

        override fun showImage(imageView: UpdateImages) {
            imageView.update(images)
        }

        override fun isSame(other: ProductItemUiState) = this.id == other.id
        override fun isContentSame(other: ProductItemUiState) = this == other
    }

    data class Liked(
        override val id: Int,
        private val title: String,
        private val price: String,
        private val images: List<String>
    ) : ProductItemUiState {

        override fun show(titleView: UpdateText, priceView: UpdateText, likeIcon: UpdateLikeIcon) {
            titleView.update(title)
            priceView.update("$price $")
            likeIcon.update(true)
        }

        override fun changeLike() = Base(id, title, price, images)

        override fun showImage(imageView: UpdateImages) {
            imageView.update(images)
        }

        override fun isSame(other: ProductItemUiState) = this.id == other.id
        override fun isContentSame(other: ProductItemUiState) = this == other


    }
}