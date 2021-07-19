package com.skillbox.skillbox.networking.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
//создаем плагин Flipper
    private val flipperNetworkPlugin = NetworkFlipperPlugin()
//создаем клиента запроса
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            CustomInterceptor()
        )
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addNetworkInterceptor(
            FlipperOkhttpInterceptor(flipperNetworkPlugin)
        )
        .build()
//функция поиска фильмов по заданным параметрам
    fun searchMovieCall(text: String): Call {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("t", text)
            .build()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }
}