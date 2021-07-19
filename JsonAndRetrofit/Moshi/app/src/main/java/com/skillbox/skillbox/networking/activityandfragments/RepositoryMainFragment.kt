package com.skillbox.skillbox.networking.activityandfragments

import android.util.Log
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.classes.MovieCustomAdapter
import com.skillbox.skillbox.networking.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
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
                        if (!responseString.contains("Error")) {
                            try {
                                //преобразуем ее в массив
                                val jsonObject = JSONObject(responseString)
                                val movieArray = jsonObject.getJSONArray("Search")
                                Log.d("response", "$movieArray")
                                //объект типа Moshi
                                val moshi = Moshi.Builder()
                                    .add(MovieCustomAdapter())
                                    .build()
                                //новый параметрезированный тип
                                val movieNewParametrizedType = Types.newParameterizedType(
                                    List::class.java,
                                    Movie::class.java
                                )
                                val adapter =
                                    moshi.adapter<List<Movie>>(movieNewParametrizedType).nonNull()
                                try {
                                    val movies = adapter.fromJson(movieArray.toString())
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

    fun addScore(position: Int){

    }
}