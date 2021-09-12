package com.skillbox.skillbox.scopedstorage.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    //    создаем объект клиента
    private val client = OkHttpClient.Builder()
//        поддерживаем перенаправления
        .followRedirects(true)
        .build()

    //    создаем объект ретрофит
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(client)
        .build()
    val api: Api
        get() = retrofit.create()
}