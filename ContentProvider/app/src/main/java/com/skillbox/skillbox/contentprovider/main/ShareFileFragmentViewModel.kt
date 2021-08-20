package com.skillbox.skillbox.contentprovider.main

import android.app.DownloadManager
import android.content.SharedPreferences
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.File

class ShareFileFragmentViewModel : ViewModel() {

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
    private val repo = ShareFileFragmentRepository()

    //    загрузка файла
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