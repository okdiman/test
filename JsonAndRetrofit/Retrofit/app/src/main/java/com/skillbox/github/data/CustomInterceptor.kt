package com.skillbox.github.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //получаем оригинальный запрос
        val originalRequest = chain.request()
        //модифицируем оригинальный запрос по нашим требованиям
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "token ${Network.accessToken}")
            .build()
        Log.i("token", Network.accessToken)
        //передаем дальше модифицированный запрос
        return chain.proceed(modifiedRequest)
    }
}