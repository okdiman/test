package com.skillbox.skillbox.flow.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.flow.classes.MovieType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var currentJob: Job? = null
    private val repo = MainFragmentRepository()

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun bind(
        queryFlow: kotlinx.coroutines.flow.Flow<String>,
        movieTypeFlow: kotlinx.coroutines.flow.Flow<MovieType>
    ) {
        currentJob?.cancel()
        combine(queryFlow, movieTypeFlow) { query, type -> query to type }
            .debounce(500)
            .mapLatest { repo.searchMovie(it) }
            .launchIn(viewModelScope).also { currentJob = it }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob = null
    }
}