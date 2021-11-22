package com.skillbox.skillbox.mainscreen.presentation.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.mainscreen.data.models.MainScreenState
import com.skillbox.skillbox.mainscreen.data.models.PhonesScreenState
import com.skillbox.skillbox.mainscreen.domain.repository.MainScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel (private val repo: MainScreenRepository) :
    ViewModel() {

    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    LiveData для получения ответа от сервера
    private val _productsLiveData = MutableLiveData<PhonesScreenState>()
    val productsLiveData: LiveData<PhonesScreenState>
        get() = _productsLiveData

    //    LiveData для получения корзины полльзователя
    private val _cartLiveData = MutableLiveData<MainScreenState>()
    val cartLiveData: LiveData<MainScreenState>
        get() = _cartLiveData

    //    получение данных для стратового экрана
    fun getMainScreenData() {
        _productsLiveData.postValue(PhonesScreenState.Loading)
        currentJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productsLiveData.postValue(PhonesScreenState.Success(repo.getMainScreenData()))
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _productsLiveData.postValue(PhonesScreenState.Error)
                }
            }
        }
            .also { currentJob = it }
    }

    //    получение корзины пользователя
    fun getCartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _cartLiveData.postValue(MainScreenState.Loading)
            try {
                _cartLiveData.postValue(MainScreenState.Success(repo.getCartInfo()))
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _cartLiveData.postValue(MainScreenState.Error)
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