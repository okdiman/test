package com.skillbox.skillbox.testonlineshop.network

import com.skillbox.skillbox.testonlineshop.classes.MainScreenRequest
import retrofit2.http.GET

interface MyApi {
    //    запрос на получение данных для главной страницы
    @GET("main")
    suspend fun getMainData(): ArrayList<MainScreenRequest>
}