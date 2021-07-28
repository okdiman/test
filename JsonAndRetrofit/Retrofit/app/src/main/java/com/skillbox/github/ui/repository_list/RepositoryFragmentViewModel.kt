package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.data.UsersRepository
import com.skillbox.github.utils.SingleLiveEvent

class RepositoryFragmentViewModel : ViewModel() {
    private val userInfoLiveData = MutableLiveData<List<UsersRepository>>()
    val userInfo: LiveData<List<UsersRepository>>
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

    private val repository = RepositoryFragmentRepository()

    fun getUsersInfo() {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        //выводим запрос в фоновый поток
        Thread {
            repository.getUsersRepoInfo(onError = isErrorCallback, onComplete = { info ->
                isLoadingLiveData.postValue(false)
                userInfoLiveData.postValue(info)
            })
        }.run()
    }

    fun getStarredRepo() {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        //выводим запрос в фоновый поток
        Thread {
            repository.getStarredRepo(onError = isErrorCallback, onComplete = { info ->
                isLoadingLiveData.postValue(false)
                userInfoLiveData.postValue(info)
            })
        }.run()
    }
}
