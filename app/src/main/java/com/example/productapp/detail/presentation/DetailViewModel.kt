package com.example.productapp.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.detail.data.DetailRepository

class DetailViewModel(
    private val repository: DetailRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    private val _liveData = MutableLiveData<DetailUiState>()
    val liveData: LiveData<DetailUiState> = _liveData

    fun init(productId: Int) {
        loadDetail(productId)
    }

    fun loadDetail(productId: Int) {
        runAsync.handleAsync<DetailUiState>(viewModelScope, {
            val data = repository.product(productId)
            val isFavorite = repository.isFavorite(productId)

            DetailUiState.Base(
                data.id,
                data.title,
                data.description,
                data.price,
                data.stock,
                data.images,
                isFavorite
            )
        }) { _liveData.value = it }
    }

    fun toggleLike(productId: Int) {
        runAsync.handleAsync<DetailUiState>(viewModelScope, {
            repository.toggleFavorite(productId)
            val data = repository.product(productId)
            val isFavorite = repository.isFavorite(productId)

            DetailUiState.Base(
                data.id,
                data.title,
                data.description,
                data.price,
                data.stock,
                data.images,
                isFavorite
            )
        }) { _liveData.value = it }
    }
}