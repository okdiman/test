package com.skillbox.skillbox.files.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.files.additionally.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.File

class DataStoreViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = DataStoreRepository(application)

    //LiveData для статуса загрузки
    private val downloadLiveData = MutableLiveData(false)
    val download: LiveData<Boolean>
        get() = downloadLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData =
        SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData

    //LiveData для выдачи финального тоста
    private val finalToastLiveData =
        SingleLiveEvent<String>()
    val isFinished: LiveData<String>
        get() = finalToastLiveData

    //скачивание файла через Network
    fun downloadFile(
        urlAddress: String,
        name: String,
        filesDir: File
    ) {
        downloadLiveData.postValue(true)
//        используем корутину для выноса запроса во внешний поток
        viewModelScope.launch {
            try {
                if (repo.downloadFile(urlAddress, name, filesDir)) {
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