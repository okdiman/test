package com.skillbox.skillbox.flow.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.database.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var currentJob: Job? = null
    private val repo = MainFragmentRepository()

    private val searchLiveData = MutableLiveData<List<MovieEntity>>()
    val searching: LiveData<List<MovieEntity>>
        get() = searchLiveData

    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun bind(
        queryFlow: Flow<String>,
        movieTypeFlow: Flow<MovieType>
    ) {
        currentJob?.cancel()
        combine(queryFlow, movieTypeFlow) { query, type -> query to type }
            .debounce(500)
            .onEach { isLoadingLiveData.postValue(true) }
            .mapLatest { searchLiveData.postValue(repo.searchMovie(it)) }
            .onEach { isLoadingLiveData.postValue(false) }
            .launchIn(viewModelScope).also { currentJob = it }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        currentJob = null
    }
}