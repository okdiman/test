package com.skillbox.skillbox.testonlineshop.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.classes.MainScreenRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {
    private val repo = MainFragmentRepository()
    private val _productsStateFlow = MutableStateFlow<MainScreenRequest?>(null)
    val productsStateFlow: StateFlow<MainScreenRequest?>
        get() = _productsStateFlow

    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    private val _isErrorLiveData = MutableLiveData<String>()
    val isErrorLiveData: LiveData<String> = _isErrorLiveData

    fun getAllProducts() {
        viewModelScope.launch {
            _isLoadingStateFlow.value = true
            try {
                _productsStateFlow.value = repo.getAllProducts()
            } catch (t: Throwable) {
                _isErrorLiveData.postValue(t.message)
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
    }
}