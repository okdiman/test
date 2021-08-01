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
    private val errorToastLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData


    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //лямбда-функция ошибки
    private val isErrorCallback: (error: String) -> Unit = {
        isLoadingLiveData.postValue(false)
        if (it.isEmpty()) {
            errorToastLiveData.postValue(it)
        }
    }

    private val repository = RepositoryFragmentRepository()

//    получение доступных пользователю репозиториев
    fun getUsersInfo() {
        isLoadingLiveData.postValue(true)
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
        //выводим запрос в фоновый поток
        Thread {
            repository.getStarredRepo(onError = isErrorCallback, onComplete = { info ->
                isLoadingLiveData.postValue(false)
                userInfoLiveData.postValue(info)
            })
        }.run()
    }
}
