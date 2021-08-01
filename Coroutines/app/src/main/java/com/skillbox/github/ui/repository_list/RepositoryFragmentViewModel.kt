package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.UsersRepository
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class RepositoryFragmentViewModel : ViewModel() {

    //LiveData для информации о репозитории
    private val publicRepoLiveData = MutableLiveData<List<UsersRepository>>()
    val publicRepo: LiveData<List<UsersRepository>>
        get() = publicRepoLiveData

    //LiveData для обработки возникших ошибок
    private val errorToastLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = errorToastLiveData


    //LiveData для обработки момента загрузки запроса
    private val isLoadingLiveData = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val repository = RepositoryFragmentRepository()

    private val scope = CoroutineScope(SupervisorJob())



    //    получение доступных пользователю репозиториев
    suspend fun getUsersInfo() {
        scope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val publicRepo = repository.getPublicRepoInfo()
                publicRepoLiveData.postValue(publicRepo)
            } catch (t: Throwable) {
                publicRepoLiveData.postValue(emptyList())
                errorToastLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    получение отмеченных пользователем репозиториев
    suspend fun getStarredRepo() {
        scope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val starredRepo = repository.getStarredRepo()
                publicRepoLiveData.postValue(starredRepo)
            } catch (t: Throwable) {
                publicRepoLiveData.postValue(emptyList())
                errorToastLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
