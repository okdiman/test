package com.skillbox.skillbox.detailsscreen.presentation.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.detailsscreen.data.models.DetailsState
import com.skillbox.skillbox.detailsscreen.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(private val repo: DetailRepository) :
    ViewModel() {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null

    //    liveData состояния экрана
    private val _detailsInfoStateLiveData = MutableLiveData<DetailsState>()
    val detailsInfoStateLiveData: LiveData<DetailsState>
        get() = _detailsInfoStateLiveData

    //    получение данных для стратового экрана
    fun getDetailsProductInfo() {
        _detailsInfoStateLiveData.postValue(DetailsState.Loading)
        currentJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _detailsInfoStateLiveData.postValue(DetailsState.Success(repo.getDetailsInfo()))
            } catch (t: Throwable) {
                Log.i("cartError", "$t")
                if (t !is kotlinx.coroutines.CancellationException) {
                    _detailsInfoStateLiveData.postValue(DetailsState.Error)
                }
            }
        }
            .also { currentJob = it }
    }

    override fun onCleared() {
        super.onCleared()
//        отменяем и очищаем job'у при уничтожении вью модели
//        (нужно если пользователь закроет вью в момент запроса)
        currentJob?.cancel()
        currentJob = null
    }
}