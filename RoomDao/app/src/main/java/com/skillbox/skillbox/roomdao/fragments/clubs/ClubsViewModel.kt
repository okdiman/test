package com.skillbox.skillbox.roomdao.fragments.clubs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ClubsViewModel(application: Application) : AndroidViewModel(application) {

    //    лайв дата получения списка всех клубов
    private val clubsListLiveData = MutableLiveData<List<Clubs>>()
    val clubsList: LiveData<List<Clubs>>
        get() = clubsListLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = ClubsRepository()

    //    получение всех клубов
    fun getAllClubs() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                clubsListLiveData.postValue(repo.getAllClubs())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    добавление нового клуба
    fun addNewClub(club: Clubs) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repo.saveNewClub(club)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllClubs()
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    удаление всех клубов
    fun deleteAllClubs() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repo.deleteAllClubs()
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllClubs()
                isLoadingLiveData.postValue(false)
            }
        }
    }
}