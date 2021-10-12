package com.skillbox.skillbox.testonlineshop.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://db2021ecom-edca.restdb.io/rest/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val api: MyApi
        get() = retrofit.create()
}