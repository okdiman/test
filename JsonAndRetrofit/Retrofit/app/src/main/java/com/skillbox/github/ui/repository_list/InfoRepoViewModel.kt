package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.utils.SingleLiveEvent

class InfoRepoViewModel : ViewModel() {

    private val infoRepoLiveData = MutableLiveData<String>()
    val infoRepo: LiveData<String>
        get() = infoRepoLiveData

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

    val repository = InfoRepoRepository()

    fun getStatus(nameRepo: String, nameOwner: String) {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        Thread {
            repository.checkRepoStatus(nameRepo, nameOwner, isErrorCallback) { info ->
                isLoadingLiveData.postValue(false)
                infoRepoLiveData.postValue(info)
            }
        }.run()
    }
}