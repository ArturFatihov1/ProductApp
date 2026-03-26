package com.example.productapp.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.detail.DetailRepository
import com.example.productapp.load.cloud.CategoryCloud
import com.example.productapp.product.data.ProductData
import com.example.productapp.product.data.ProductRepository
import com.example.productapp.product.presentation.ProductItemUiState
import com.example.productapp.product.presentation.ProductListUiState

class ProductListViewModel(
    private val repository: ProductRepository,
    private val detailRepository: DetailRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<ProductListUiState>()
    val navigationCommand = MutableLiveData<Int?>()

    val categoriesLiveData = MutableLiveData<List<CategoryCloud>>()

    var currentQuery: String = ""
    var currentCategory: String = "All"

    fun init() {
        if (categoriesLiveData.value == null) fetchCategories()
        if (liveData.value == null) fetchProducts()
    }

    private fun fetchCategories() {
        runAsync.handleAsync(viewModelScope, {
            repository.getCategories()
        }) { categories ->
            val allCategory = CategoryCloud(slug = "All", name = "Все категории")
            categoriesLiveData.value = listOf(allCategory) + categories
        }
    }

    fun filterByCategory(categorySlug: String) {
        currentCategory = categorySlug
        currentQuery = ""

        runAsync.handleAsync(viewModelScope, {
            val products = if (categorySlug == "All") {
                repository.products()
            } else {
                repository.productsByCategory(categorySlug)
            }
            mapToState(products, currentQuery)
        }) { state -> liveData.value = state }
    }

    fun search(query: String) {
        currentQuery = query
        currentCategory = "All"

        if (query.isEmpty()) {
            fetchProducts()
            return
        }
        runAsync.handleAsync(viewModelScope, {
            val products = repository.search(query)
            mapToState(products, query)
        }) { state -> liveData.value = state }
    }

    fun fetchProducts() {
        currentQuery = ""
        currentCategory = "All"
        runAsync.handleAsync(viewModelScope, {
            val products = repository.products()
            mapToState(products, "")
        }) { state -> liveData.value = state }
    }

    private suspend fun mapToState(products: List<ProductData>, query: String): ProductListUiState {
        val favCount = repository.favoritesCount()
        val items = products.map { data ->
            if (repository.isFavorite(data.id))
                ProductItemUiState.Liked(data.id, data.title, data.price.toString(), data.images)
            else
                ProductItemUiState.Base(data.id, data.title, data.price.toString(), data.images)
        }
        return ProductListUiState.Base(items, favCount, query)
    }

    fun toggleLike(productId: Int) {
        val currentState = liveData.value as? ProductListUiState.Base
        currentState?.let { state ->
            val updatedList = state.products.map {
                if (it.id == productId) it.changeLike() else it
            }
            val wasLiked = state.products.find { it.id == productId } is ProductItemUiState.Liked
            val newCount = if (wasLiked) state.favoriteCount - 1 else state.favoriteCount + 1

            liveData.value = state.copy(products = updatedList, favoriteCount = newCount)
        }
        runAsync.handleAsync(viewModelScope, { repository.toggleFavorite(productId) }) {}
    }

    fun openDetail(id: Int) {
        detailRepository.saveId(id)
        navigationCommand.value = id
    }

    fun onNavigationDone() {
        navigationCommand.value = null
    }
}