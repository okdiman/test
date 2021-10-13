package com.skillbox.skillbox.testonlineshop.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.classes.MainScreenResponseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {
    private val repo = MainFragmentRepository()
    private val _productsStateFlow = MutableStateFlow<MainScreenResponseWrapper?>(null)
    val productsStateFlow: StateFlow<MainScreenResponseWrapper?>
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
                Log.i("responceError", t.message.toString())
                _isErrorLiveData.postValue(t.message)
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
    }
}