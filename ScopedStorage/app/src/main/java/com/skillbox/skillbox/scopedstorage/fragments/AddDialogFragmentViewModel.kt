package com.skillbox.skillbox.scopedstorage.fragments

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.scopedstorage.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDialogFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val videoDownloadedLiveData = SingleLiveEvent<Boolean>()
    val videoDownloaded: LiveData<Boolean>
        get() = videoDownloadedLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = AddDialogFragmentRepository(application)

    fun downloadVideo(title: String, url: String, uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingLiveData.postValue(true)
            try {
                videoDownloadedLiveData.postValue(repo.downloadVideoFromNetwork(title, url, uri))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}