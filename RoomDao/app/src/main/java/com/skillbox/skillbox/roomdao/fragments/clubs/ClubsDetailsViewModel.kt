package com.skillbox.skillbox.roomdao.fragments.clubs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ClubsDetailsViewModel(application: Application) : AndroidViewModel(application) {

    //    лайв дата получения списка стадионов
    private val getAllStadiumsLiveData = MutableLiveData<List<Stadiums>>()
    val getAllStadiums: LiveData<List<Stadiums>>
        get() = getAllStadiumsLiveData

    //    лайв дата получения одного стадиона
    private val getStadiumLiveData = MutableLiveData<Stadiums>()
    val getStadium: LiveData<Stadiums>
        get() = getStadiumLiveData

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

    //    создание объекта репозитория
    private val repo = ClubsDetailsRepository()

    //    удаление клуба
    fun deleteClub(club: Clubs) {
//        устанавливаем стартовые статусы лайв дат
        isLoadingLiveData.postValue(true)
        deleteClubLiveData.postValue(false)
//        создаем корутину для использования саспенд функции
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

    //    обновление клуба
    fun updateClub(club: Clubs) {
//        устанавливаем стартовые статус лайв даты загрузки
        isLoadingLiveData.postValue(true)
//        создаем корутину для использования саспенд функции
        viewModelScope.launch {
            try {
                repo.updateClub(club)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    получение стадиона по id
    fun getStadiumById(stadiumId: Long) {
        //        устанавливаем стартовые статус лайв даты загрузки
        isLoadingLiveData.postValue(true)
        //        создаем корутину для использования саспенд функции
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

    //    получение стадиона по названию
    fun getStadiumByName(stadiumName: String) {
//        устанавливаем стартовые статус лайв даты загрузки
        isLoadingLiveData.postValue(true)
//        создаем корутину для использования саспенд функции
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

    //    получение списка всех стадионов
    fun getAllStadiums() {
//        устанавливаем стартовые статус лайв даты загрузки
        isLoadingLiveData.postValue(true)
//        создаем корутину для использования саспенд функции
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

    //    добавление нового стадиона
    fun addNewStadium(stadium: List<Stadiums>) {
//        устанавливаем стартовые статус лайв даты загрузки
        isLoadingLiveData.postValue(true)
//        создаем корутину для использования саспенд функции
        viewModelScope.launch {
            try {
                repo.addNewStadium(stadium)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                getAllStadiums()
                isLoadingLiveData.postValue(false)
            }
        }
    }
}