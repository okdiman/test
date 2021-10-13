package com.skillbox.skillbox.testonlineshop.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.classes.MainScreenResponseWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {
    private val repo = MainFragmentRepository()

    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    StateFlow для получения ответа от сервера
    private val _productsStateFlow = MutableStateFlow<MainScreenResponseWrapper?>(null)
    val productsStateFlow: StateFlow<MainScreenResponseWrapper?>
        get() = _productsStateFlow

    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    лайв дата ошибок
    private val _isErrorLiveData = MutableLiveData<String>()
    val isErrorLiveData: LiveData<String> = _isErrorLiveData

    //    получение данных для стратового экрана
    fun getMainScreenData() {
        currentJob?.cancel()
        viewModelScope.launch {
            _isLoadingStateFlow.value = true
            try {
                _productsStateFlow.value = repo.getMainScreenData()
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