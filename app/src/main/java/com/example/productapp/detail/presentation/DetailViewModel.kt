package com.example.productapp.detail.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.detail.data.DetailRepository

class DetailViewModel(
    private val repository: DetailRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<DetailUiState>()

    fun init() {
        if (liveData.value == null) {
            loadDetail()
        }
    }

    fun loadDetail() {
        runAsync.handleAsync<DetailUiState>(viewModelScope, {
            val data = repository.product()
            val isFavorite = repository.isFavorite()
            DetailUiState.Base(
                data.id,
                data.title,
                data.description,
                data.price,
                data.stock,
                data.images,
                isFavorite
            )
        }) { liveData.value = it }
    }

    fun toggleLike() {
        runAsync.handleAsync<DetailUiState>(viewModelScope, {
            repository.toggleFavorite()
            val data = repository.product()
            val isFavorite = repository.isFavorite()
            DetailUiState.Base(
                data.id,
                data.title,
                data.description,
                data.price,
                data.stock,
                data.images,
                isFavorite
            )
        }) { liveData.value = it }
    }
}