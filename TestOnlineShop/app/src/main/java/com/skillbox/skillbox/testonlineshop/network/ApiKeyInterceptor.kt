package com.skillbox.skillbox.testonlineshop.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //получаем оригинальный запрос
        val originalRequest = chain.request()
        //модифицируем оригинальный запрос по нашим требованиям
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("apikey:", apiKey)
            .build()
        Log.i("token", apiKey)
        //передаем дальше модифицированный запрос
        return chain.proceed(modifiedRequest)
    }
}