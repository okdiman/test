package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.data.UsersRepository
import com.skillbox.github.utils.SingleLiveEvent

class RepositoryFragmentViewModel : ViewModel() {

    //LiveData для информации о репозитории
    private val userInfoLiveData = MutableLiveData<List<UsersRepository>>()
    val userInfo: LiveData<List<UsersRepository>>
        get() = userInfoLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData = SingleLiveEvent<Boolean>()
    val isError: LiveData<Boolean>
        get() = errorToastLiveData
    var getError: String = ""

    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //лямбда-функция ошибки
    private val isErrorCallback: (error: String) -> Unit = {
        isLoadingLiveData.postValue(false)
        if (it.isNotEmpty()) {
            getError = it
            errorToastLiveData.postValue(true)
        }
    }

    private val repository = RepositoryFragmentRepository()

//    получение доступных пользователю репозиториев
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

//    получение отмеченных пользователем репозиториев
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
