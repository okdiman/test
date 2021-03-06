package com.skillbox.github.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.utils.SingleLiveEvent

class CurrentUserViewModel : ViewModel() {

    //LiveData для пользовательской информации
    private val userInfoLiveData = MutableLiveData<String>()
    val userInfo: LiveData<String>
        get() = userInfoLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData

    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //лямбда-функция ошибки
    private val isErrorCallback: (error: String) -> Unit = {
        isLoadingLiveData.postValue(false)
        if (it.isEmpty()) {
            errorToastLiveData.postValue(it)
        }
    }

    private val repository = CurrentUserRepository()

    //получение информации о пользователе
    fun getUsersInfo() {
        isLoadingLiveData.postValue(true)
        //выводим запрос в фоновый поток
        Thread {
            repository.getUsersInfo(onError = isErrorCallback, onComplete = { info ->
                isLoadingLiveData.postValue(false)
                userInfoLiveData.postValue(info)
            })
        }.run()
    }
}