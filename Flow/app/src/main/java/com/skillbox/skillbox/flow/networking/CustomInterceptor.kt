package com.skillbox.skillbox.flow.networking

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//получаем оригинальный запрос
        val originalRequest = chain.request()
//создаем новый URL на основе оригинального, добавляя туда queryParameter
        val url = originalRequest.url().newBuilder()
            .addQueryParameter("apikey", MOVIE_API_KEY)
            .build()
//модифицируем оригинальный запрос по нашим требованиям
        val modifiedRequest = originalRequest.newBuilder()
            .url(url)
            .build()
//передаем дальше модифицированный запрос
        return chain.proceed(modifiedRequest)
    }
}