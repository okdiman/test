package com.skillbox.skillbox.networking.activityandfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.classes.SingleLiveEvent
import okhttp3.Call

class ViewModelMainFragment : ViewModel() {
    //создаес LiveData для фильмов и для статуса загрузки
    private val movieLiveData = SingleLiveEvent<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = movieLiveData

    private val isLoadingLiveData = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val errorToastLiveData = SingleLiveEvent<Boolean>()
    val isError: LiveData<Boolean>
        get() = errorToastLiveData
    var gotError: String = ""

    private val repository = RepositoryMainFragment()

    private var currentCall: Call? = null

    private val isErrorCallback: (error: String) -> Unit = {
        if (it.isNotEmpty()) {
            gotError = it
            errorToastLiveData.postValue(true)
        }
    }

    //выполнение запроса
    fun requestMovies(text: String) {
        gotError = ""
        errorToastLiveData.postValue(false)
        isLoadingLiveData.postValue(true)
        //выводим запрос в фоновый поток
        Thread {
            currentCall =
                repository.requestMovieByTitle(text, isErrorCallback) { movie ->
                    isLoadingLiveData.postValue(false)
                    movieLiveData.postValue(movie)
                    currentCall = null
                }
        }.run()
    }

    //добавление оценки
    fun addScore(position: Int, source: String, value: String) {
        repository.addScore(movieLiveData.value!![position], source, value)
    }

    //очищаем запрос в случае закрытия пользователем приложения
    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}