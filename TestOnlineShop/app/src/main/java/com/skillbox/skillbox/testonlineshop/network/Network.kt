package com.skillbox.skillbox.testonlineshop.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    //    создамем клиент okHttp
    private val client = OkHttpClient.Builder()
//        добавляем наш интерцептор для добавления ключа
        .addInterceptor(ApiKeyInterceptor())
        .build()

    //    создаем объект ретрофита
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://db2021ecom-edca.restdb.io/rest/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    //    связываем наш Api интерфейс и ретрофит
    val api: MyApi
        get() = retrofit.create()
}