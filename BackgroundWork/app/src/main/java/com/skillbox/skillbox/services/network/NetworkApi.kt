package com.skillbox.skillbox.services.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkApi {
    @GET
    suspend fun downloadFile(
        @Url url: String
    ): ResponseBody
}