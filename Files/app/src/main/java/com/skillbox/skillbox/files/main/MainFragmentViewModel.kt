package com.skillbox.skillbox.files.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.files.additionally.SingleLiveEvent
import kotlinx.coroutines.launch

class MainFragmentViewModel() : ViewModel() {
    private val downloadLiveData = MutableLiveData<Boolean>(false)
    val download: LiveData<Boolean>
        get() = downloadLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData =
        SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData

    //LiveData для обработки возникших ошибок
    private val finalToastLiveData =
        SingleLiveEvent<String>()
    val isFinished: LiveData<String>
        get() = finalToastLiveData


    private val repo = MainFragmentRepository()

    fun downloadFile(url: String, context: Context) {
        downloadLiveData.postValue(true)
        viewModelScope.launch {
            try {
                repo.downloadFile(url, context)
                finalToastLiveData.postValue("File was downloaded")
            } catch (t: Throwable) {
                errorToastLiveData.postValue("Something wrong, file was deleted:(")
            } finally {
                downloadLiveData.postValue(false)
            }
        }
    }
}