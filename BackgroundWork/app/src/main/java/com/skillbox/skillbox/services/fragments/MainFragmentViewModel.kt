package com.skillbox.skillbox.services.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    //    stateFlow ошибок
    private val _isErrorStateFlow = MutableStateFlow("")
    val isErrorStateFlow: StateFlow<String> = _isErrorStateFlow

    private val repo = MainFragmentRepository(application)

    //    загрузка файла
    fun downloadFile(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.downloadFile(url)
            } catch (t: Throwable) {
                _isErrorStateFlow.value = t.message.toString()
            }
        }
    }

    //    периодическая задача
    fun startPeriodicWork() {
        repo.periodicWork()
    }
}