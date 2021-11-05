package com.skillbox.skillbox.testonlineshop.features.main.presentation.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.features.general.domain.repository.Repository
import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.features.main.domain.repository.MainScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repo: MainScreenRepository) : ViewModel() {


    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    StateFlow для получения ответа от сервера
    private val _productsStateFlow = MutableStateFlow<MainScreenResponseWrapper?>(null)
    val productsStateFlow: StateFlow<MainScreenResponseWrapper?>
        get() = _productsStateFlow

    //    LiveData для получения корзины полльзователя
    private val _cartLiveData = MutableLiveData<CartDetailsWrapper>()
    val cartLiveData: LiveData<CartDetailsWrapper>
        get() = _cartLiveData


    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    stateFlow ошибок
    private val _isErrorLiveData = MutableStateFlow(false)
    val isErrorLiveData: StateFlow<Boolean> = _isErrorLiveData

    //    получение данных для стратового экрана
    fun getMainScreenData() {
        _isErrorLiveData.value = false
        currentJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _productsStateFlow.value = repo.getMainScreenData()
                _cartLiveData.postValue(repo.getCartInfo())
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _isErrorLiveData.value = true
                }
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
            .also { currentJob = it }
    }

    //    получение корзины пользователя
    fun getCartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _cartLiveData.postValue(repo.getCartInfo())
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _isErrorLiveData.value = true
                }
            } finally {
                _isErrorLiveData.value = false
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