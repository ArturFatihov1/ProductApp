package com.example.productapp.favorite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.favorite.data.FavoriteRepository
import com.example.productapp.product.presentation.ProductItemUiState

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<FavoriteUiState>()

    fun init() {
        loadFavorites()
    }

    fun loadFavorites() {
        runAsync.handleAsync<FavoriteUiState>(viewModelScope, {
            val favorites = repository.favorites()
            val uiItems = favorites.map {
                ProductItemUiState.Liked(it.id, it.title, it.price.toString(), it.images)
            }
            FavoriteUiState.Base(uiItems)
        }) { state ->
            liveData.value = state
        }
    }

    fun toggleLike(id: Int) {
        runAsync.handleAsync<FavoriteUiState>(viewModelScope, {
            repository.toggleFavorite(id)
            val currentItems = (liveData.value as? FavoriteUiState.Base)?.favorites ?: emptyList()
            val newList = currentItems.filter { it.id != id }
            FavoriteUiState.Base(newList)
        }) { state ->
            liveData.value = state
        }
    }
}