package com.skillbox.skillbox.files.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
//    запрос на скачивание файла из сети
    @GET
    suspend fun getFile(
        @Url url: String
    ): ResponseBody
}