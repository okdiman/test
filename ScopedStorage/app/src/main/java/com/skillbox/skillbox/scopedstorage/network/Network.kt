package com.skillbox.skillbox.scopedstorage.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder()
        .followRedirects(true)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(client)
        .build()
    val api: Api
        get() = retrofit.create()
}