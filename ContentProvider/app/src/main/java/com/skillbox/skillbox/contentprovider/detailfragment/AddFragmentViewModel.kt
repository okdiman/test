package com.skillbox.skillbox.contentprovider.detailfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.contentprovider.main.GeneralRepository
import kotlinx.coroutines.launch

class AddFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val gettingOfNewContactLiveData = MutableLiveData<Boolean>()
    val gettingOfNewContact: LiveData<Boolean>
        get() = gettingOfNewContactLiveData

    private val repo = GeneralRepository(application)
    fun addNewContact(name: String, phone: String, email: String) {
        gettingOfNewContactLiveData.postValue(false)
        viewModelScope.launch {
//            try {
                repo.saveContact(name, phone, email)
                gettingOfNewContactLiveData.postValue(true)
//            } catch (t: Throwable) {
//
//            }
        }
    }

}