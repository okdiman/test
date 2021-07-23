package com.skillbox.github.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.utils.SingleLiveEvent

class CurrentUserViewModel : ViewModel() {
    private val userInfoLiveData = MutableLiveData<String>()
    val userInfo: LiveData<String>
        get() = userInfoLiveData

    private val errorToastLiveData = SingleLiveEvent<Boolean>()
    val isError: LiveData<Boolean>
        get() = errorToastLiveData
    var getError: String = ""

    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData


    private val isErrorCallback: (error: String) -> Unit = {
        isLoadingLiveData.postValue(false)
        if (it.isNotEmpty()) {
            getError = it
            errorToastLiveData.postValue(true)
        }
    }

    private val repository = CurrentUserRepository()


    fun getUsersInfo() {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        //выводим запрос в фоновый поток
        Thread {
            repository.getUsersInfo(onError = isErrorCallback, onComplete = { info ->
                isLoadingLiveData.postValue(false)
                userInfoLiveData.postValue(info)
            })
        }.run()
    }
}