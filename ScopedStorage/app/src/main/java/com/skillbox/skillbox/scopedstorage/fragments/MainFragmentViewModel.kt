package com.skillbox.skillbox.scopedstorage.fragments

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import com.skillbox.skillbox.scopedstorage.utils.SingleLiveEvent
import com.skillbox.skillbox.scopedstorage.utils.haveQ
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var pendingDeleteId: Long? = null

    private var isObservingStarted: Boolean = false

    private val videoListLiveData = MutableLiveData<List<VideoForList>>()
    val videoForList: LiveData<List<VideoForList>>
        get() = videoListLiveData

    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()
    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

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
        }
        getAllVideos()
    }

    private fun getAllVideos() {
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

    fun deleteVideo(id: Long) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repo.deleteVideo(id)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
                if (haveQ() && t is RecoverableSecurityException) {
                    pendingDeleteId = id
                    recoverableActionMutableLiveData.postValue(t.userAction)
                } else {
                    isErrorLiveData.postValue(t.message)
                }
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteVideo(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null
    }
}