package com.skillbox.skillbox.testonlineshop.presentation.mainfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.domain.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.data.RepositoryImpl
import com.skillbox.skillbox.testonlineshop.domain.models.CartDetailsWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val repo = RepositoryImpl()

    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    StateFlow для получения ответа от сервера
    private val _productsStateFlow = MutableStateFlow<MainScreenResponseWrapper?>(null)
    val productsStateFlow: StateFlow<MainScreenResponseWrapper?>
        get() = _productsStateFlow

    private val _cartLiveData = MutableLiveData<CartDetailsWrapper>()
    val cartLiveData: LiveData<CartDetailsWrapper>
        get() = _cartLiveData


    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    лайв дата ошибок
    private val _isErrorLiveData = MutableLiveData<String>()
    val isErrorLiveData: LiveData<String> = _isErrorLiveData

    //    получение данных для стратового экрана
    fun getMainScreenData() {
        currentJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _productsStateFlow.value = repo.getMainScreenData()
                _cartLiveData.postValue(repo.getCartInfo())
            } catch (t: Throwable) {
                _isErrorLiveData.postValue(t.message)
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
            .also { currentJob = it }
    }

    fun getCartData(){
        currentJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _cartLiveData.postValue(repo.getCartInfo())
            } catch (t: Throwable) {
                _isErrorLiveData.postValue(t.message)
            } finally {
                _isLoadingStateFlow.value = false
            }
        }
            .also { currentJob = it }
    }

    override fun onCleared() {
        super.onCleared()
//        отменяем и очищаем job'у при уничтожении вью модели
        currentJob?.cancel()
        currentJob = null
    }
}