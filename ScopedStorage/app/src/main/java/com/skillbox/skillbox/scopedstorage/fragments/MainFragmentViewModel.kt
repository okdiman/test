package com.skillbox.skillbox.scopedstorage.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import com.skillbox.skillbox.scopedstorage.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val videoListLiveData = MutableLiveData<List<VideoForList>>()
    val videoForList: LiveData<List<VideoForList>>
        get() = videoListLiveData

    private var isObservingStarted: Boolean = false

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = MainFragmentRepository(application)

    override fun onCleared() {
        super.onCleared()
        repo.unregisterObserver()
    }

    fun isObserving() {
        if (isObservingStarted.not()) {
            repo.observeVideos { getAllVideos() }
            isObservingStarted = true
        } else {
            getAllVideos()
        }
    }

    fun getAllVideos() {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                videoListLiveData.postValue(repo.getVideoList())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}