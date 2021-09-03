package com.skillbox.skillbox.roomdao.fragments.stadiums

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.entities.Attendance
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class StadiumDetailsViewModel(application: Application) : AndroidViewModel(application) {

    //    лайв дата получения стадиона
    private val getStadiumLiveData = MutableLiveData<StadiumsWithAttendance>()
    val getStadium: LiveData<StadiumsWithAttendance>
        get() = getStadiumLiveData

    //    лайв дата удаления стадиона
    private val deleteLiveData = MutableLiveData<Boolean>()
    val delete: LiveData<Boolean>
        get() = deleteLiveData

    //    лайв дата обновления стадиона
    private val updateLiveData = SingleLiveEvent<Boolean>()
    val update: SingleLiveEvent<Boolean>
        get() = updateLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    private val repo = StadiumDetailsRepository()

    //    получение стадиона вместе с посещаемостью
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

    //    удаление стадиона
    fun deleteStadium(stadiums: Stadiums) {
        isLoadingLiveData.postValue(true)
        deleteLiveData.postValue(false)
        viewModelScope.launch {
            try {
                repo.deleteStadium(stadiums)
                deleteLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    обновление посещаемости
    fun changeAttendance(attendance: Attendance) {
        isLoadingLiveData.postValue(true)
        updateLiveData.postValue(false)
        viewModelScope.launch {
            try {
                repo.changeAttendance(attendance)
                updateLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

}