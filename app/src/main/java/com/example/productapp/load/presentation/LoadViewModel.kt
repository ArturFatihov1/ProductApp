package com.example.productapp.load.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.core.RunAsync
import com.example.productapp.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    private val _liveData = MutableLiveData<LoadUiState>()
    val liveData: LiveData<LoadUiState> = _liveData

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun || _liveData.value is LoadUiState.Error) {
            _liveData.value = LoadUiState.Progress

            runAsync.handleAsync(viewModelScope, {
                try {
                    repository.load()
                    LoadUiState.Success
                } catch (e: Exception) {
                    LoadUiState.ErrorRes()
                }
            }) { state ->
                _liveData.value = state
            }
        }
    }
}