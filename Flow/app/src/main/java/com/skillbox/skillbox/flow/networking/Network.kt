package com.skillbox.skillbox.flow.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")

        .client(client)
        .build()
    val api: OmbdApi
        get() = retrofit.create()
}