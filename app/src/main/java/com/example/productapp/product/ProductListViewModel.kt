package com.example.productapp.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync

class ProductListViewModel(
    private val repository: ProductRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<ProductListUiState>()

    fun init() {
        if (liveData.value == null) fetchProducts()
    }

    fun search(query: String) {
        fetchProducts(query)
    }

    fun fetchProducts(query: String = "") {
        runAsync.handleAsync(viewModelScope, {
            val products = repository.products(query)
            ProductListUiState.Base(products)
        }) { state ->
            liveData.value = state
        }
    }

    fun toggleLike(productId: Int) {
        runAsync.handleAsync(viewModelScope, {
            repository.toggleFavorite(productId)
            val products = repository.products()
            ProductListUiState.Base(products)
        }) { state ->
            liveData.value = state
        }
    }
}