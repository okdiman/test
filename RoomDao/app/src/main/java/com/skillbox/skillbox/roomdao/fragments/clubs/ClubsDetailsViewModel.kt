package com.skillbox.skillbox.roomdao.fragments.clubs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ClubsDetailsViewModel(application: Application) : AndroidViewModel(application) {
    //    лайв дата успешности удаления
    private val deleteClubLiveData = MutableLiveData<Boolean>()
    val deleteClub: LiveData<Boolean>
        get() = deleteClubLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    val repo = ClubsDetailsRepository()

    fun deleteClub(club: Clubs) {
        isLoadingLiveData.postValue(true)
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
}