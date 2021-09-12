package com.skillbox.skillbox.scopedstorage.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
//    метод получения файла из сети
    @GET
    suspend fun getFile(
        @Url url: String
    ): ResponseBody
}