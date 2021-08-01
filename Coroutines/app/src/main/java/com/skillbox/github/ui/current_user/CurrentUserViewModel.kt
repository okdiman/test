package com.skillbox.github.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CurrentUserViewModel : ViewModel() {

    //LiveData для пользовательской информации
    private val userInfoLiveData = MutableLiveData<String>()
    val userInfo: LiveData<String>
        get() = userInfoLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData

    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val repository = CurrentUserRepository()

    //получение информации о пользователе
    fun getUsersInfo() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            errorToastLiveData.postValue(throwable.message)
        }
        viewModelScope.launch(exceptionHandler) {
            isLoadingLiveData.postValue(true)
            val infoUser = async {
                repository.getUsersInfo()
            }
            val usersFollowings = async {
                repository.getUsersFollowing()
            }
            val info = infoUser.await()
            val followings = usersFollowings.await()
            userInfoLiveData.postValue("$info " +
                    "$followings")
            isLoadingLiveData.postValue(false)
        }
    }
}