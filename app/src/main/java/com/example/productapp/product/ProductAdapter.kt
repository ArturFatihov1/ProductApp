package com.example.productapp.product


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.databinding.ItemProductLayoutBinding
import com.example.productapp.views.imageButton.UpdateLikeIcon
import com.example.productapp.views.text.UpdateText

class ProductAdapter(
    private val listener: ProductClickListener,
) : ListAdapter<ProductItemUiState, ProductAdapter.ProductViewHolder>(ProductDiffCallback()),
    UpdateProductList {

    override fun update(list: List<ProductItemUiState>) {
        submitList(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ProductViewHolder(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ProductItemUiState) {
            binding.itemView.setOnClickListener { listener.click(item.id) }
            binding.likeButton.setOnClickListener { listener.onLikeClick(item.id) }
            item.showImage(binding.imageProduct)
            item.show(
                titleView = object : UpdateText {
                    override fun update(text: String) {
                        binding.title.text = text
                    }
                },
                priceView = object : UpdateText {
                    override fun update(text: String) {
                        binding.price.text = text
                    }
                },
                likeIcon = object : UpdateLikeIcon {
                    override fun update(isLiked: Boolean) {
                        val iconRes =
                            if (isLiked) R.drawable.ic_like_selected else R.drawable.ic_like_unselected
                        binding.likeButton.setImageResource(iconRes)
                    }
                }
            )
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductItemUiState>() {
    override fun areItemsTheSame(
        oldItem: ProductItemUiState,
        newItem: ProductItemUiState
    ): Boolean {
        return oldItem.isSame(newItem)
    }

    override fun areContentsTheSame(
        oldItem: ProductItemUiState,
        newItem: ProductItemUiState
    ): Boolean {
        return oldItem.isContentSame(newItem)
    }
}

interface ProductClickListener {
    fun click(id: Int)
    fun onLikeClick(id: Int)
}