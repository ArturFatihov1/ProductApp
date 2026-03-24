package com.example.productapp.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<FavoriteUiState>()

    fun init() {
        runAsync.handleAsync(viewModelScope, {
            val favorites = repository.favorites()
            FavoriteUiState.Base(favorites)
        }) { state ->
            liveData.value = state
        }
    }

    fun toggleLike(productId: Int) {
        runAsync.handleAsync(viewModelScope, {
            repository.toggleFavorite(productId)
            val favorites = repository.favorites()
            FavoriteUiState.Base(favorites)
        }) { state ->
            liveData.value = state
        }
    }
}