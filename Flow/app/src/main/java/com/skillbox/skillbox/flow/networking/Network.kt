package com.skillbox.skillbox.flow.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    //    создаем объект okhttp клиента
    private val client = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor())
        .build()

    //    создаем объект ретрофита
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val api: OmdbApi
        get() = retrofit.create()
}