package com.skillbox.skillbox.roomdao.fragments.clubs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ClubsDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllStadiumsLiveData = MutableLiveData<List<Stadiums>>()
    val getAllStadiums: LiveData<List<Stadiums>>
        get() = getAllStadiumsLiveData

    private val getStadiumLiveData = MutableLiveData<Stadiums>()
    val getStadium: LiveData<Stadiums>
        get() = getStadiumLiveData

    //    лайв дата успешности удаления
    private val deleteClubLiveData = MutableLiveData<Boolean>()
    val deleteClub: LiveData<Boolean>
        get() = deleteClubLiveData

    private val updateClubLiveData = MutableLiveData<Boolean>()
    val updateClub: LiveData<Boolean>
        get() = updateClubLiveData


    private val successLiveData = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = successLiveData


    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = ClubsDetailsRepository()

    fun deleteClub(club: Clubs) {
        isLoadingLiveData.postValue(true)
        deleteClubLiveData.postValue(false)
        viewModelScope.launch {
            try {
                deleteClubLiveData.postValue(repo.deleteClub(club))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun updateClub(club: Clubs) {
        isLoadingLiveData.postValue(true)
        updateClubLiveData.postValue(false)
        viewModelScope.launch {
            try {
                updateClubLiveData.postValue(repo.updateClub(club))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getStadiumById(stadiumId: Long) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                getStadiumLiveData.postValue(repo.getStadiumById(stadiumId))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getStadiumByName(stadiumName: String) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                getStadiumLiveData.postValue(repo.getStadiumByName(stadiumName))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getAllStadiums() {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                getAllStadiumsLiveData.postValue(repo.getAllStadiums())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun addNewStadium(stadium: List<Stadiums>) {
        isLoadingLiveData.postValue(true)
        successLiveData.postValue(false)
        viewModelScope.launch {
            try {
                successLiveData.postValue(repo.addNewStadium(stadium))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllStadiums()
                isLoadingLiveData.postValue(false)
            }
        }
    }
}