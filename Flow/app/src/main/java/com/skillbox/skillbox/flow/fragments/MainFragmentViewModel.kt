package com.skillbox.skillbox.flow.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.database.MovieEntity
import com.skillbox.skillbox.flow.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    //    создаем нуллабельную Job'у, чтобы мы могли завершить ее, в случае прерывания ее работы
    private var currentJob: Job? = null
    private val repo = MainFragmentRepository()

    //    лайв дата результатов поиска
    private val searchLiveData = MutableLiveData<List<MovieEntity>>()
    val searching: LiveData<List<MovieEntity>>
        get() = searchLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    flow поиск фильмов
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun bind(
        queryFlow: Flow<String>,
        movieTypeFlow: Flow<MovieType>
    ) {
//        завершаем предыдущую job'у
        currentJob?.cancel()
//        комбинируем 2 пришедших флоу
        combine(queryFlow, movieTypeFlow) { query, type -> query to type }
//                устанавливаем таймаут 500млсек, чтобы не кидать запросы на каждый введенный символ,
//                а только тогда, когда пользователь предположительно закончил вводить текст
            .debounce(500)
//                устанавливаем статус загрузки
            .onEach { isLoadingLiveData.postValue(true) }
//                получаем список фильмов
            .mapLatest { searchLiveData.postValue(repo.searchMovie(it)) }
//                убираем статус загрузки
            .onEach { isLoadingLiveData.postValue(false) }
//                все процессы выше проводим на IO диспетчере
            .flowOn(Dispatchers.IO)
//                в случае ошибки передаем ее в лайв дату ошибок
            .catch { isErrorLiveData.postValue(it.message) }
//                активируем флоу, так же устанавливаем действующую job'у для переменной
            .launchIn(viewModelScope).also { currentJob = it }
    }

    override fun onCleared() {
        super.onCleared()
//        отменяем и очищаем job'у при уничтожении вью модели
        currentJob?.cancel()
        currentJob = null
    }
}