package com.skillbox.skillbox.files.main

import android.app.DownloadManager
import android.content.SharedPreferences
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.files.additionally.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.File

class MainFragmentViewModel : ViewModel() {

    //LiveData для статуса загрузки
    private val downloadLiveData = MutableLiveData(false)
    val download: LiveData<Boolean>
        get() = downloadLiveData

    //LiveData для статуса загрузки через downloadManger
    private val downloadByDownloadManagerLiveData = MutableLiveData(false)
    val downloadByDownloadManager: LiveData<Boolean>
        get() = downloadByDownloadManagerLiveData

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

    //репозиторий
    private val repo = MainFragmentRepository()

    //скачивание файла через Network
    fun downloadFile(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File
    ) {
        downloadLiveData.postValue(true)
//        используем корутину для выноса запроса во внешний поток
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

    fun downloadFromDownloadManager(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File,
        downloadManager: DownloadManager,
        loader: ProgressBar
    ) {
        downloadByDownloadManagerLiveData.postValue(true)
//        используем корутину для выноса запроса во внешний поток
        viewModelScope.launch {
            try {
                if (repo.downloadFileByDownloadManager(
                        urlAddress,
                        name,
                        sharedPrefs,
                        filesDir,
                        downloadManager,
                        loader
                    )
                ) {
                    finalToastLiveData.postValue("File was downloaded")
                } else {
                    errorToastLiveData.postValue("Something wrong, file wasn't downloaded:(")
                }
            } catch (t: Throwable) {
                errorToastLiveData.postValue("${t.message}")
            } finally {
                downloadByDownloadManagerLiveData.postValue(false)
            }
        }
    }
}