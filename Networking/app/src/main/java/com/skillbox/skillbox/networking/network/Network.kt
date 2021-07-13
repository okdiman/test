package com.skillbox.skillbox.networking.network

import com.skillbox.skillbox.networking.extensions.MOVIE_API_KEY
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    fun searchMovieCall(text: String): Call {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", MOVIE_API_KEY)
            .addQueryParameter("s", text)
            .build()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }
}