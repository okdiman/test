package com.skillbox.skillbox.networking.network

import com.skillbox.skillbox.networking.files.MOVIE_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//получаем оригинальный запрос
        val originalRequest = chain.request()
//модифицируем оригинальный запрос по нашим требованиям
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("apikey", MOVIE_API_KEY)
            .build()
//передаем дальше модифицированный запрос
        return chain.proceed(modifiedRequest)
    }
}