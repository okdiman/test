package com.skillbox.skillbox.roomdao.fragments.stadiums

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class StadiumDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val getStadiumLiveData = MutableLiveData<Stadiums>()
    val getStadium: LiveData<Stadiums>
        get() = getStadiumLiveData


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

    private val repo = StadiumDetailsRepository()

    fun getStadiumWithAttendance(stadiumName: String) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                getStadiumLiveData.postValue(repo.getStadiumAndAttendance(stadiumName))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun deleteStadium(stadiums: Stadiums) {
        isLoadingLiveData.postValue(true)
        successLiveData.postValue(false)
        viewModelScope.launch {
            try {
                repo.deleteStadium(stadiums)
                successLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

}