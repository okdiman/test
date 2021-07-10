package com.skillbox.skillbox.networking.activityandfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.networking.classes.Movie

class ViewModelMainFragment: ViewModel() {
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = movieLiveData

    private val repository = RepositoryMainFragment()

    fun requestMovies(){

    }
}