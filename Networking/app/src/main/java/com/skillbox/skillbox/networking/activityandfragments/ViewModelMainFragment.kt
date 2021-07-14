package com.skillbox.skillbox.networking.activityandfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.networking.classes.Movie
import okhttp3.Call

class ViewModelMainFragment : ViewModel() {
    //создаес LiveData для фильмов и для статуса загрузки
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = movieLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val repository = RepositoryMainFragment()

    private var currentCall: Call? = null

    //выполнение запроса
    fun requestMovies(text: String, year: String, type: String) {
        isLoadingLiveData.postValue(true)
        //выводим запрос в фоновый поток
        Thread {
            currentCall = repository.requestMovieByTitle(text, year, type) { movies ->
                isLoadingLiveData.postValue(false)
                movieLiveData.postValue(movies)
                currentCall = null
            }
        }.run()
    }

    //очищаем запрос в случае закрытия пользователем приложения
    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}