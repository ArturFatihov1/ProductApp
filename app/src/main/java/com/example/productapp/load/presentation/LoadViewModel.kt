package com.example.productapp.load.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<LoadUiState>()

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun || liveData.value is LoadUiState.Error) {
            liveData.value = LoadUiState.Progress

            runAsync.handleAsync(viewModelScope, {
                try {
                    repository.load()
                    LoadUiState.Success
                } catch (e: Exception) {
                    LoadUiState.ErrorRes()
                }
            }) { state ->
                liveData.value = state
            }
        }
    }
}