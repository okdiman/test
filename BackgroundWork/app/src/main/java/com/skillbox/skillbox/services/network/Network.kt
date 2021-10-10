package com.skillbox.skillbox.services.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder()
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(client)
        .build()
    val api: NetworkApi
        get() = retrofit.create()
}