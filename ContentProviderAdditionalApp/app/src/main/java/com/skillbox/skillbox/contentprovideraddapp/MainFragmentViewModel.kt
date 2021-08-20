package com.skillbox.skillbox.contentprovideraddapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel (application: Application) : AndroidViewModel(application) {

    //    создаем лайв дату для передачи списка контактов
    private val contactListLiveData = MutableLiveData<List<User>>()
    val contactList: LiveData<List<User>>
        get() = contactListLiveData

    //    лайв дата успешности удаления контакта
    private val deletingOfContact = MutableLiveData<Boolean>()
    val deleting: LiveData<Boolean>
        get() = deletingOfContact

    //    лайв дата успешности загрузки
    private val gettingOfNewContactLiveData = MutableLiveData<Boolean>()
    val gettingOfNewContact: LiveData<Boolean>
        get() = gettingOfNewContactLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    репозиторий
    private val repo = MainFragmentRepository(application)

    //    добавление контакта
    fun addNewContact(name: String, age: Int) {
//        устанавливаем значения в лайв даты
        isLoadingLiveData.postValue(true)
        gettingOfNewContactLiveData.postValue(false)
//        используем корутину для suspend функций
        viewModelScope.launch {
            try {
//                пытаемся сохранить контакт
                repo.saveUser(name, age)
                gettingOfNewContactLiveData.postValue(true)
            } catch (t: Throwable) {
//                в случае ошибки оповещаем лайв дату ошибок
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату загрузки о завершении работы
                isLoadingLiveData.postValue(false)
            }
        }
    }

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

    //    удаление контакта из списка
    fun deleteContactFromMemory(id: Long) {
//    устанавливаем статусы лайв дат
        isLoadingLiveData.postValue(true)
        deletingOfContact.postValue(false)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                выполняем удаление контакта и оповещаем об успешности операции лайв дату в случае отсутсвия ошибок
                repo.deleteContact(id)
                deletingOfContact.postValue(true)
            } catch (t: Throwable) {
//                оповещаем лайв дату ошибки об ошибке
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату об окончании процесса удаления
                isLoadingLiveData.postValue(false)
            }
        }
    }

}