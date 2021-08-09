package com.skillbox.skillbox.files.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.files.additionally.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.File

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

    fun downloadFile(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File
    ) {
        downloadLiveData.postValue(true)
        viewModelScope.launch {
            try {
                if (repo.downloadFile(urlAddress, name, sharedPrefs, filesDir)) {
                    finalToastLiveData.postValue("File was downloaded")
                } else {
                    errorToastLiveData.postValue("Something wrong, file wasn't downloaded:(")
                }
            } catch (t: Throwable) {
                errorToastLiveData.postValue("${t.message}")
            } finally {
                downloadLiveData.postValue(false)
            }
        }
    }
}