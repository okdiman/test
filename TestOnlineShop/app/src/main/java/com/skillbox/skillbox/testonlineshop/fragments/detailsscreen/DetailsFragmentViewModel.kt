package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.fragments.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsFragmentViewModel : ViewModel() {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null
    private val repo = Repository()
    private val _detailsInfoStateFlow = MutableStateFlow<Product?>(null)
    val detailsInfoStateFlow: StateFlow<Product?>
        get() = _detailsInfoStateFlow

    //    stateFlow статуса загрузки
    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    //    лайв дата ошибок
    private val _isErrorLiveData = MutableLiveData<String>()
    val isErrorLiveData: LiveData<String> = _isErrorLiveData

    //    получение данных для стратового экрана
    fun getDetailsProductInfo() {
        currentJob?.cancel()
        viewModelScope.launch {
            _isLoadingStateFlow.value = true
            try {
                _detailsInfoStateFlow.value = repo.getDetailsInfo()
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