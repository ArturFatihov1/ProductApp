package com.example.productapp.load

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync

class LoadViewModel(
    private val repository: LoadRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    val liveData = MutableLiveData<LoadUiState>()

    fun init(isFirstRun: Boolean = true) {
        if (isFirstRun && liveData.value == null) {
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