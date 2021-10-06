package com.skillbox.skillbox.flow.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.flow.database.MovieEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DatabaseFragmentViewModel : ViewModel() {
    private val repo = MainFragmentRepository()

    private val moviesListLiveData = MutableLiveData<List<MovieEntity>>()
    val moviesList: LiveData<List<MovieEntity>>
        get() = moviesListLiveData

//    инициализация работы с flow обновления списка фильмов
    init {
        repo.observeMovies()
            .onEach { moviesListLiveData.postValue(it) }
            .launchIn(viewModelScope)
    }
}