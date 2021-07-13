package com.skillbox.skillbox.networking.activityandfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.networking.classes.Movie
import okhttp3.Call

class ViewModelMainFragment : ViewModel() {
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = movieLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val repository = RepositoryMainFragment()

    private var currentCall: Call? = null

    fun requestMovies(text: String) {
        isLoadingLiveData.postValue(true)
        Thread {
            currentCall = repository.requestMovieByTitle(text) { movies ->
                isLoadingLiveData.postValue(false)
                movieLiveData.postValue(movies)
                currentCall = null
            }
        }.run()
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}