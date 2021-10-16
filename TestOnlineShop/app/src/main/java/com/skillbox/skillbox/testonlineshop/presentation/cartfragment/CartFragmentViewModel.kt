package com.skillbox.skillbox.testonlineshop.presentation.cartfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.data.RepositoryImpl
import com.skillbox.skillbox.testonlineshop.data.models.CartDetailsWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartFragmentViewModel : ViewModel() {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null
    val repo = RepositoryImpl()
    private val _cartStateFlow = MutableStateFlow<CartDetailsWrapper?>(null)
    val cartStateFlow: StateFlow<CartDetailsWrapper?>
        get() = _cartStateFlow

    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    лайв дата ошибок
    private val _isErrorLiveData = MutableLiveData<String>()
    val isErrorLiveData: LiveData<String> = _isErrorLiveData

    fun getCartInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingStateFlow.value = true
            try {
                _cartStateFlow.value = repo.getCartInfo()
            } catch (t: Throwable) {
                _isErrorLiveData.postValue(t.message)
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