package com.skillbox.skillbox.contentprovider.detailfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.main.GeneralRepository
import kotlinx.coroutines.launch


class DetailInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val contactLiveData = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = contactLiveData

    private val deletingOfContact = MutableLiveData<Boolean>()
    val deleting: LiveData<Boolean>
        get() = deletingOfContact

    private val repo = GeneralRepository(application)

    fun getContactData(contactId: Long, name: String) {
        viewModelScope.launch {
//            try {
                repo.getContactInfo(contactId, name)
//            } catch (t: Throwable) {
//
//            }
        }
    }

    fun deleteContactFromMemory(contactId: Long) {
        deletingOfContact.postValue(false)
        viewModelScope.launch {
            try {

            } catch (t:Throwable){

            }
        }
    }
}