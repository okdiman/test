package com.skillbox.skillbox.contentprovider.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    //    создаем лайв дату для передачи списка контактов
    private val contactListLiveData = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>>
        get() = contactListLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    получаем репозиторий
    private val repo = MainRepository(application)

    //    получение списка контактов
    fun getAllContacts() {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    создаем корутину для работы с suspend функциями
        viewModelScope.launch {
            try {
//                получаем список контактов
                contactListLiveData.postValue(repo.getAllContacts())
            } catch (t: Throwable) {
//                в случае ошибки передаем ошибку в лайв дату ошибки
                contactListLiveData.postValue(emptyList())
                isErrorLiveData.postValue(t.message)
            } finally {
//                сообщаем о завершении загрузки
                isLoadingLiveData.postValue(false)
            }
        }
    }
}