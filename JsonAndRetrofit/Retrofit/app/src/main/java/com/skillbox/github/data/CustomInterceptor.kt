package com.skillbox.github.data

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //получаем оригинальный запрос
        val originalRequest = chain.request()
        //модифицируем оригинальный запрос по нашим требованиям
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", Network.accessToken)
            .build()
        //передаем дальше модифицированный запрос
        return chain.proceed(modifiedRequest)
    }
}