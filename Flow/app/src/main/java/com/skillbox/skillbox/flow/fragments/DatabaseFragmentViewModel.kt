package com.skillbox.skillbox.flow.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.flow.database.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DatabaseFragmentViewModel : ViewModel() {
    private val repo = MainFragmentRepository()

    //    stateFlow для списка фильмов
    private val _moviesListStateFlow = MutableStateFlow(emptyList<MovieEntity>())
    val moviesListStateFlow: StateFlow<List<MovieEntity>> = _moviesListStateFlow


    //    инициализация работы с flow обновления списка фильмов
    init {
        repo.observeMovies()
            .onEach { _moviesListStateFlow.value = it }
            .launchIn(viewModelScope)
    }
}