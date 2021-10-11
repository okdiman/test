package com.skillbox.skillbox.services.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    //    создаем объект okHttpClient'a
    private val client = OkHttpClient.Builder()
        .build()

    //    создаем объект Retrofit'a
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(client)
        .build()

    //    связываем наш api интерфейс и retrofit
    val api: NetworkApi
        get() = retrofit.create()
}