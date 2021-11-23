package com.skillbox.skillbox.testonlineshop.components.di

import android.util.Log
import com.skillbox.skillbox.core.utils.apiKey
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//общий модуль
val generalModule = module {
    single(named("apiKey")) {
        Interceptor { chain ->
            //получаем оригинальный запрос
            val originalRequest = chain.request()
            //добавляем header нашего ключа
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("x-apikey", apiKey)
                .build()
            Log.i("token", apiKey)
            //передаем дальше модифицированный запрос
            chain.proceed(modifiedRequest)
        }
    }

    single(named("okHttpClient")) {
        OkHttpClient.Builder()
//        добавляем наш интерцептор для добавления ключа
            .addInterceptor(get(named("apiKey")))
            .build()
    }

    single(named("retrofit")) {
        Retrofit.Builder()
            .baseUrl("https://db2021ecom-edca.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named("okHttpClient")))
            .build()
    }
}