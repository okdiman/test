package com.skillbox.skillbox.networking.activityandfragments

import android.util.Log
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RepositoryMainFragment {
    //выполнение запроса данных из сети
    fun requestMovieByTitle(
        text: String,
        year: String,
        type: String,
        callback: (List<Movie>) -> Unit
    ): Call {
        return Network.searchMovieCall(text, year, type).apply {
            enqueue(object : Callback {
                //обрабатываем ошибку запроса
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    callback(emptyList())
                }

                //обрабатываем отсутсвие ошибок при запросе
                override fun onResponse(call: Call, response: Response) {
                    //обрабатываем успешность запроса
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val moshi = Moshi.Builder().build()
                        val movieNewParametrizedType = Types.newParameterizedType(
                            List::class.java,
                            Movie::class.java
                        )
                        val adapter = moshi.adapter<List<Movie>>(movieNewParametrizedType).nonNull()
                        try {
                            val movies = adapter.fromJson(responseString)
                            if (movies != null) {
                                callback(movies)
                            } else {
                                callback(emptyList())
                            }
                        } catch (e: Exception) {
                            Log.e("server", "${e.message}")
                            callback(emptyList())
                        }
                    } else {
                        callback(emptyList())
                    }
                }
            })
        }
    }
}