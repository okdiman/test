package com.skillbox.skillbox.testonlineshop.features.cart.presentation.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartState
import com.skillbox.skillbox.testonlineshop.features.cart.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(private val repo: CartRepository) : ViewModel() {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    LiveData для получения корзины пользователя
    private val _cartLiveData = MutableLiveData<CartState>()
    val cartLiveData: LiveData<CartState>
        get() = _cartLiveData

    //    получение данных корзины пользователя
    fun getCartInfo() {
        _cartLiveData.postValue(CartState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartLiveData.postValue(CartState.Success(repo.getCartInfo()))
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _cartLiveData.postValue(CartState.Error)
                }
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