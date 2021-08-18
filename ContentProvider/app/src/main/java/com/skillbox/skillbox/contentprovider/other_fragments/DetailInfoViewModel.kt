package com.skillbox.skillbox.contentprovider.other_fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class DetailInfoViewModel(application: Application) : AndroidViewModel(application) {

    //    лайв дата данных контакта
    private val contactLiveData = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = contactLiveData

    //    лайв дата успешности удаления контакта
    private val deletingOfContact = MutableLiveData<Boolean>()
    val deleting: LiveData<Boolean>
        get() = deletingOfContact

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    создаем репозиторий
    private val repo = DetailsFragmentRepository(application)

    //    получение списка контактов
    fun getContactData(contactId: Long, name: String) {
//    оповещаем о статусе загрузки
        isLoadingLiveData.postValue(true)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                устанавливаем значения, полученные из функции репозитория в лайв дату данных контакта
                contactLiveData.postValue(repo.getContactInfo(contactId, name))
            } catch (t: Throwable) {
//                в случае ошибки оповещаем лайв дайту ошибок
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем об окончании загрузки
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    удаление контакта из списка
    fun deleteContactFromMemory(contact: Contact) {
//    устанавливаем статусы лайв дат
        isLoadingLiveData.postValue(true)
        deletingOfContact.postValue(false)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                выполняем удаление контакта и оповещаем об успешности операции лайв дату в случае отсутсвия ошибок
                repo.deleteContact(contact)
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