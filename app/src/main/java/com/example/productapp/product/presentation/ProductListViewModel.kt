package com.example.productapp.product.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.load.cloud.CategoryDTO
import com.example.productapp.product.data.ProductData
import com.example.productapp.product.data.ProductRepository

class ProductListViewModel(
    private val repository: ProductRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<ProductListUiState>()
    val categoriesLiveData = MutableLiveData<List<CategoryDTO>>()

    fun init() {
        if (categoriesLiveData.value == null) {
            runAsync.handleAsync(viewModelScope, {
                listOf(CategoryDTO("All", "Все категории")) + repository.getCategories()
            }) { categoriesLiveData.value = it }
        }
        if (liveData.value == null) fetchProducts()
    }

    fun fetchProducts() = runAsync.handleAsync(viewModelScope, {
        mapToState(repository.products())
    }) { liveData.value = it }

    fun search(query: String) = if (query.isEmpty()) fetchProducts() else {
        runAsync.handleAsync(viewModelScope, {
            mapToState(repository.search(query), query)
        }) { liveData.value = it }
    }

    fun filterByCategory(categorySlug: String) = if (categorySlug == "All") fetchProducts() else {
        runAsync.handleAsync(viewModelScope, {
            mapToState(repository.productsByCategory(categorySlug))
        }) { liveData.value = it }
    }

    fun toggleLike(productId: Int) {
        (liveData.value as? ProductListUiState.Base)?.let { state ->
            val wasLiked = state.products.find { it.id == productId } is ProductItemUiState.Liked
            val newCount = if (wasLiked) state.favoriteCount - 1 else state.favoriteCount + 1
            val updatedList = state.products.map { if (it.id == productId) it.changeLike() else it }

            liveData.value = state.copy(products = updatedList, favoriteCount = newCount)
            runAsync.handleAsync(viewModelScope, { repository.toggleFavorite(productId) }) {}
        }
    }

    private suspend fun mapToState(
        products: List<ProductData>,
        query: String = ""
    ): ProductListUiState {
        val favCount = repository.favoritesCount()
        val items = products.map { data ->
            if (repository.isFavorite(data.id))
                ProductItemUiState.Liked(data.id, data.title, data.price.toString(), data.images)
            else
                ProductItemUiState.Base(data.id, data.title, data.price.toString(), data.images)
        }
        return ProductListUiState.Base(items, favCount, query)
    }
}