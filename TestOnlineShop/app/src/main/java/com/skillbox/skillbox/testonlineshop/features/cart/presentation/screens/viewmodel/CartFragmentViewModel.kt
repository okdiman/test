package com.skillbox.skillbox.testonlineshop.features.cart.presentation.screens.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.cart.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(private val repo: CartRepository) : ViewModel() {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    stateFlow для получения корзины пользователя
    private val _cartStateFlow = MutableStateFlow<CartDetailsWrapper?>(null)
    val cartStateFlow: StateFlow<CartDetailsWrapper?>
        get() = _cartStateFlow

    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    stateFlow ошибок
    private val _isErrorLiveData = MutableStateFlow(false)
    val isErrorLiveData: StateFlow<Boolean> = _isErrorLiveData

    //    получение данных корзины пользователя
    fun getCartInfo() {
        _isErrorLiveData.value = false
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _cartStateFlow.value = repo.getCartInfo()
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _isErrorLiveData.value = true
                }
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
//        отменяем и очищаем job'у при уничтожении вью модели
        currentJob?.cancel()
        currentJob = null
    }
}