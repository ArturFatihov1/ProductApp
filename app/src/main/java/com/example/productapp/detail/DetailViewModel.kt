package com.example.productapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync

class DetailViewModel(
    private val productId: Int,
    private val repository: DetailRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<DetailUiState>()

    fun init() {
        if (liveData.value == null) {
            runAsync.handleAsync<DetailUiState>(viewModelScope, {
                val data = repository.product(productId)
                val isFavorite = repository.isFavorite(productId)

                DetailUiState.Base(
                    id = data.id,
                    title = data.title,
                    description = data.description,
                    price = data.price,
                    stock = data.stock,
                    images = data.images,
                    isFavorite = isFavorite
                )
            }) { state ->
                liveData.value = state
            }
        }
    }

    fun toggleLike() {
        runAsync.handleAsync<DetailUiState>(viewModelScope, {
            repository.toggleFavorite(productId)

            val data = repository.product(productId)
            val isFavorite = repository.isFavorite(productId)

            DetailUiState.Base(
                id = data.id,
                title = data.title,
                description = data.description,
                price = data.price,
                stock = data.stock,
                images = data.images,
                isFavorite = isFavorite
            )
        }) { state ->
            liveData.value = state
        }
    }
}