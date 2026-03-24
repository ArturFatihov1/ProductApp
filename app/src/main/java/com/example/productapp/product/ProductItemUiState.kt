package com.example.productapp.product

interface ProductItemUiState {
    val id: Int
    val title: String
    val price: Double

    fun changeLike(): ProductItemUiState

    fun isSame(other: ProductItemUiState): Boolean
    fun isContentSame(other: ProductItemUiState): Boolean

    fun show(
        titleView: UpdateText,
        priceView: UpdateText,
        likeIcon: UpdateLikeIcon
    )

    data class Base(
        override val id: Int,
        override val title: String,
        override val price: Double
    ) : ProductItemUiState {
        override fun changeLike() = Liked(id, title, price)

        override fun isSame(other: ProductItemUiState) = this.id == other.id
        override fun isContentSame(other: ProductItemUiState) = this == other

        override fun show(titleView: UpdateText, priceView: UpdateText, likeIcon: UpdateLikeIcon) {
            titleView.update(title)
            priceView.update(price.toString())
            likeIcon.update(false)
        }
    }

    data class Liked(
        override val id: Int,
        override val title: String,
        override val price: Double
    ) : ProductItemUiState {
        override fun changeLike() = Base(id, title, price)

        override fun isSame(other: ProductItemUiState) = this.id == other.id
        override fun isContentSame(other: ProductItemUiState) = this == other

        override fun show(titleView: UpdateText, priceView: UpdateText, likeIcon: UpdateLikeIcon) {
            titleView.update(title)
            priceView.update(price.toString())
            likeIcon.update(true)
        }
    }
}