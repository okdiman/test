package com.skillbox.skillbox.networking.activityandfragments

import android.util.Log
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.classes.MovieCustomAdapter
import com.skillbox.skillbox.networking.network.Network
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Call
import okhttp3.Callback
import okhttp3.RequestBody.Companion.toRequestBody
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
                        responseString.toRequestBody()
                        Log.e("server", responseString)
                        if (!responseString.contains("Error")) {
                            try {
                                val adapter = createMoshiAndAdapter()
                                try {
                                    val movies = adapter.fromJson(responseString)
                                    if (movies != null) {
                                        callback(movies)
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
                            errorCallback("Фильмы не найдены")
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

    fun addScore(movie: Movie, source: String, value: String) {
        movie.scores.put(source, value)
        try {
            val adapter = createMoshiAndAdapter()
            val movieWithNewScore = adapter.toJson(listOf(movie))
            Log.i("score", movieWithNewScore)
        } catch (e: Exception) {
            Log.e("score", "${e.message}")
        }

    }

    private fun createMoshiAndAdapter(): JsonAdapter<List<Movie>> {
        //объект типа Moshi
        val moshi = Moshi.Builder()
            .add(MovieCustomAdapter())
            .build()
        //новый параметрезированный тип
        val movieNewParametrizedType = Types.newParameterizedType(
            List::class.java,
            Movie::class.java
        )
        return moshi.adapter<List<Movie>>(movieNewParametrizedType).nonNull()
    }
}