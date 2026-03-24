package com.example.productapp.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.detail.DetailRepository

class ProductListViewModel(
    private val repository: ProductRepository,
    private val detailRepository: DetailRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<ProductListUiState>()
    val navigationCommand = MutableLiveData<Int?>()
    var currentQuery: String = ""

    fun init() {
        if (liveData.value == null) fetchProducts()
    }

    fun search(query: String) {
        currentQuery = query
        fetchProducts()
    }

    fun fetchProducts() {
        runAsync.handleAsync<ProductListUiState>(viewModelScope, {
            val products: List<ProductData> = repository.products(currentQuery)
            val favCount = repository.favoritesCount()

            val items = products.map { data ->
                if (repository.isFavorite(data.id))
                    ProductItemUiState.Liked(
                        data.id,
                        data.title,
                        data.price.toString(),
                        data.images
                    )
                else
                    ProductItemUiState.Base(
                        data.id,
                        data.title,
                        data.price.toString(),
                        data.images
                    )
            }
            ProductListUiState.Base(items, favCount, currentQuery)
        }) { state ->
            liveData.value = state
        }
    }

    fun toggleLike(productId: Int) {
        val currentState = liveData.value as? ProductListUiState.Base
        currentState?.let { state ->
            val updatedList = state.products.map {
                if (it.id == productId) it.changeLike() else it
            }

            val item = state.products.find { it.id == productId }
            val wasLiked = item is ProductItemUiState.Liked
            val newCount = if (wasLiked) state.favoriteCount - 1 else state.favoriteCount + 1

            liveData.value = state.copy(products = updatedList, favoriteCount = newCount)
        }

        runAsync.handleAsync(viewModelScope, {
            repository.toggleFavorite(productId)
        }) {
        }
    }

    fun openDetail(id: Int) {
        detailRepository.saveId(id)
        navigationCommand.value = id
    }

    fun onNavigationDone() {
        navigationCommand.value = null
    }
}