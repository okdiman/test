package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class TournamentsViewModel(application: Application) : AndroidViewModel(application) {

    //    лайв дата получения списка турниров
    private val tournamentsListLiveData = MutableLiveData<List<Tournaments>>()
    val tournamentsList: LiveData<List<Tournaments>>
        get() = tournamentsListLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = TournamentsRepository()

    //    получение всех турниров
    fun getAllTournaments() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                tournamentsListLiveData.postValue(repo.getAllTournaments())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    добавление нового турнира
    fun addNewTournament(tournament: Tournaments) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repo.saveNewTournament(tournament)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllTournaments()
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    удаление всех турниров
    fun deleteAllTournaments() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                repo.deleteAllTournaments()
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllTournaments()
                isLoadingLiveData.postValue(false)
            }
        }
    }
}