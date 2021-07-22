package com.skillbox.skillbox.networking.activityandfragments

import android.util.Log
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.classes.MovieCustomAdapter
import com.skillbox.skillbox.networking.network.Network
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RepositoryMainFragment {
    //выполнение запроса данных из сети
    fun requestMovieByTitle(
        text: String,
        errorCallback: (String) -> Unit,
        callback: (List<Movie>) -> Unit
    ): Call {
        return Network.searchMovieCall(text).apply {
            enqueue(object : Callback {
                //обрабатываем ошибку запроса
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    callback(emptyList())
                    errorCallback("Ошибка запроса")
                }

                //обрабатываем отсутсвие ошибок при запросе
                override fun onResponse(call: Call, response: Response) {
                    //обрабатываем успешность запроса
                    if (response.isSuccessful) {
                        //получаем строку тела запроса
                        val responseString = response.body?.string().orEmpty()
                        Log.e("server", responseString)
                        try {
                            val adapter = createMoshiAndAdapter()
                            try {
                                val movies = adapter.fromJson(responseString)
                                if (movies != null) {
                                    callback(listOf(movies))
                                } else {
                                    errorCallback("Фильмы не найдены")
                                    callback(emptyList())
                                }
                            } catch (e: Exception) {
                                Log.e("server", "${e.message}")
                                errorCallback("ошибка ${e.message}")
                                callback(emptyList())
                            }
                        } catch (e: Exception) {
                            Log.e("server", "${e.message}")
                            errorCallback("${e.message}")
                            callback(emptyList())
                        }
                    } else {
                        errorCallback("Запрос не выполнен")
                        callback(emptyList())
                    }
                }
            })
        }
    }

    //добавление оценки и вывод ее в лог
    fun addScore(movie: Movie, source: String, value: String, callback: (List<Movie>) -> Unit) {
        val changedMovie = movie.copy(scores = movie.scores.toMutableMap())
        changedMovie.scores.put(source, value)
        try {
            val adapter = createMoshiAndAdapter()
            val movieWithNewScore = adapter.toJson(movie)
            Log.i("score", movieWithNewScore)
            callback(listOf(changedMovie))
        } catch (e: Exception) {
            Log.e("score", "${e.message}")
        }
    }

    //создание адаптера
    private fun createMoshiAndAdapter(): JsonAdapter<Movie> {
        //объект типа Moshi
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()
        return moshi.adapter(Movie::class.java).nonNull()
    }
}