package com.skillbox.skillbox.contentprovider.otherfragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AddFragmentViewModel(application: Application) : AndroidViewModel(application) {

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
    private val repo = AddContactRepository(application)

    //    добавление контакта
    fun addNewContact(name: String, phone: String, email: String) {
//        устанавливаем значения в лайв даты
        isLoadingLiveData.postValue(true)
        gettingOfNewContactLiveData.postValue(false)
//        используем корутину для suspend функций
        viewModelScope.launch {
            try {
//                пытаемся сохранить контакт
                repo.saveContact(name, phone, email)
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

}