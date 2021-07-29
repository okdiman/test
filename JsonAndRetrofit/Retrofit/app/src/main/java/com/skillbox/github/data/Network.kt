package com.skillbox.github.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {

    //создаем переменную токена для дальнешей инициализации
    lateinit var accessToken: String

    //создаем клиента okhttp
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor())
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    //создаем переменную типа retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    //переменная для связи retrofit c интерфейсом запросов
    val githubApi: GithubInterface
        get() = retrofit.create()
}