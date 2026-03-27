package com.example.productapp.product.presentation


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.databinding.ItemProductLayoutBinding

class ProductAdapter(
    private val listener: ProductClickListener,
) : ListAdapter<ProductItemUiState, ProductAdapter.ProductViewHolder>(ProductDiffCallback()),
    UpdateProductList {

    override fun update(newList: List<ProductItemUiState>) {
        submitList(newList)
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
            binding.itemView.setOnClickListener {
                listener.click(item.id)
                Log.d("Adapter", "Click triggered for ID: ${item.id}")
            }
            binding.likeButton.setOnClickListener { listener.onLikeClick(item.id) }
            item.showImage(binding.imageProduct)
            item.show(
                titleView = binding.title,
                priceView = binding.price,
                likeIcon = binding.likeButton
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