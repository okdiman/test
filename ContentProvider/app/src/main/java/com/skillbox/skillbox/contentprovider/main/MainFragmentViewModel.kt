package com.skillbox.skillbox.contentprovider.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.classes.Contact
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val contactListLiveData = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>>
        get() = contactListLiveData

    private val repo = MainFragmentRepository(application)

    fun getAllContacts() {
        viewModelScope.launch {
            try {
                contactListLiveData.postValue(repo.getAllContacts())
            } catch (t: Throwable) {
                contactListLiveData.postValue(emptyList())
            }
        }
    }
}