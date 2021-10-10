package com.skillbox.skillbox.services.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _downloadStateFlow = MutableStateFlow(false)
    val downloadStateFlow: StateFlow<Boolean>
        get() = _downloadStateFlow

    //    stateFlow ошибок
    private val _isErrorStateFlow = MutableStateFlow("")
    val isErrorStateFlow: StateFlow<String> = _isErrorStateFlow
    private val repo = MainFragmentRepository(application)
    fun downloadFile(url: String) {
        viewModelScope.launch {
            _downloadStateFlow.value = true
            try {
                repo.downloadFile(url)
            } catch (t: Throwable) {
                _isErrorStateFlow.value = t.message.toString()
            } finally {
                _downloadStateFlow.value = false
            }
        }
    }
}