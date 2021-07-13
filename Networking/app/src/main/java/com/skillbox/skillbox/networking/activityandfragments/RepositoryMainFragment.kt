package com.skillbox.skillbox.networking.activityandfragments

import android.util.Log
import com.skillbox.skillbox.networking.classes.Movie
import com.skillbox.skillbox.networking.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RepositoryMainFragment {
    fun requestMovieByTitle(text: String, callback: (List<Movie>) -> Unit): Call {
        return Network.searchMovieCall(text).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    callback(emptyList())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = parseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        callback(emptyList())
                    }
                }
            })
        }
    }


    private fun parseMovieResponse(responseBodeString: String): List<Movie> {
        return try {
            val jsonObject = JSONObject(responseBodeString)
            val movieArray = jsonObject.getJSONArray("Search")
            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    val poster = movieJsonObject.getString("Poster")
                    val type = movieJsonObject.getString("Type")
                    Movie(title, year, type, id, poster)
                }
        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }
}