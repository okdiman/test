package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.utils.SingleLiveEvent

class InfoRepoViewModel : ViewModel() {

    //LiveData для информации о репозитории
    private val infoRepoLiveData = MutableLiveData<String>()
    val infoRepo: LiveData<String>
        get() = infoRepoLiveData

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

    val repository = InfoRepoRepository()

//    получение статуса отметки
    fun getStatus(nameRepo: String, nameOwner: String) {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        Thread {
            repository.checkRepoStatus(nameRepo, nameOwner, isErrorCallback) { info ->
                isLoadingLiveData.postValue(false)
//                обработка ответов сервера
                when (info) {
                    404 -> infoRepoLiveData.postValue("Repository is not starred by u")
                    403 -> infoRepoLiveData.postValue("Forbidden")
                    401 -> infoRepoLiveData.postValue("Unauthorized")
                    304 -> infoRepoLiveData.postValue("Not Modified")
                    204 -> infoRepoLiveData.postValue("Repository is starred by u")
                    else -> infoRepoLiveData.postValue("Incorrect status code")
                }
            }
        }.run()
    }

//    добавление отметки
    fun addStar(nameRepo: String, nameOwner: String) {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        Thread {
            repository.addStar(nameRepo, nameOwner, isErrorCallback) { info ->
                isLoadingLiveData.postValue(false)
                when (info) {
//                    обработка ответов сервера
                    404 -> infoRepoLiveData.postValue("Resource not found")
                    403 -> infoRepoLiveData.postValue("Forbidden")
                    401 -> infoRepoLiveData.postValue("Unauthorized")
                    304 -> infoRepoLiveData.postValue("Not Modified")
                    204 -> infoRepoLiveData.postValue("U have stared this repository now")
                    else -> infoRepoLiveData.postValue("Incorrect status code")
                }
            }
        }.run()
    }

//    удаление отметки
    fun delStar(nameRepo: String, nameOwner: String) {
        isLoadingLiveData.postValue(true)
        getError = ""
        errorToastLiveData.postValue(false)
        Thread {
            repository.delStar(nameRepo, nameOwner, isErrorCallback) { info ->
                isLoadingLiveData.postValue(false)
                when (info) {
//                    обработка ответов сервера
                    404 -> infoRepoLiveData.postValue("Resource not found")
                    403 -> infoRepoLiveData.postValue("Forbidden")
                    401 -> infoRepoLiveData.postValue("Unauthorized")
                    304 -> infoRepoLiveData.postValue("Not Modified")
                    204 -> infoRepoLiveData.postValue("U have unstared this repository now")
                    else -> infoRepoLiveData.postValue("Incorrect status code")
                }
            }
        }.run()
    }
}