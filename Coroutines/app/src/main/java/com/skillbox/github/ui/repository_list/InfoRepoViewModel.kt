package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class InfoRepoViewModel : ViewModel() {

    //LiveData для информации о репозитории
    private val infoRepoLiveData = MutableLiveData<String>()
    val infoRepo: LiveData<String>
        get() = infoRepoLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData

    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //лямбда-функция ошибки
    private val isErrorCallback: (error: String) -> Unit = {
        isLoadingLiveData.postValue(false)
        if (it.isEmpty()) {
            errorToastLiveData.postValue(it)
        }
    }

    private val repository = InfoRepoRepository()

    //    получение статуса отметки
    fun getStatus(nameRepo: String, nameOwner: String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repository.checkRepoStatus(nameRepo, nameOwner, isErrorCallback) { info ->
//                обработка ответов сервера
                    when (info) {
                        404 -> infoRepoLiveData.postValue("Repository is not starred by u")
                        403 -> infoRepoLiveData.postValue("Forbidden")
                        401 -> infoRepoLiveData.postValue("Unauthorized")
                        304 -> infoRepoLiveData.postValue("Not Modified")
                        204 -> infoRepoLiveData.postValue("Repository is starred by u")
                        else -> infoRepoLiveData.postValue("Incorrect status code")
                    }
                    isLoadingLiveData.postValue(false)
                }
            } catch (t: Throwable) {
                errorToastLiveData.postValue(t.message)
            }
        }
    }

    //    добавление отметки
    fun addStar(nameRepo: String, nameOwner: String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                when (repository.addStar(nameRepo, nameOwner)) {
//                  обработка ответов сервера
                    404 -> infoRepoLiveData.postValue("Resource not found")
                    403 -> infoRepoLiveData.postValue("Forbidden")
                    401 -> infoRepoLiveData.postValue("Unauthorized")
                    304 -> infoRepoLiveData.postValue("Not Modified")
                    204 -> infoRepoLiveData.postValue("U have stared this repository now")
                    else -> infoRepoLiveData.postValue("Incorrect status code")
                }
            } catch (t: Throwable) {
                errorToastLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }


    //    удаление отметки
    fun delStar(nameRepo: String, nameOwner: String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                when (repository.delStar(nameRepo, nameOwner)) {
//                  обработка ответов сервера
                    404 -> infoRepoLiveData.postValue("Resource not found")
                    403 -> infoRepoLiveData.postValue("Forbidden")
                    401 -> infoRepoLiveData.postValue("Unauthorized")
                    304 -> infoRepoLiveData.postValue("Not Modified")
                    204 -> infoRepoLiveData.postValue("U have unstarred this repository now")
                    else -> infoRepoLiveData.postValue("Incorrect status code")
                }
            } catch (t: Throwable) {
                errorToastLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}
