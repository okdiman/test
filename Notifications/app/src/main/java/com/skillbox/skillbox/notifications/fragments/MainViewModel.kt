package com.skillbox.skillbox.notifications.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.notifications.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = MainFragmentRepository()
    private val tokenLiveData = MutableLiveData<String?>()
    val token: LiveData<String?>
        get() = tokenLiveData

    //    лайв дата ошибок
    private val isErrorLiveData =
        SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    получение токена
    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                tokenLiveData.postValue(repo.getToken())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            }
        }
    }
}