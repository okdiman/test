package com.skillbox.skillbox.testonlineshop.data.network

import com.skillbox.skillbox.testonlineshop.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.data.models.Product
import retrofit2.http.GET

interface MyApi {
    //    запрос на получение данных для главной страницы
    @GET("main")
    suspend fun getMainData(): ArrayList<MainScreenResponseWrapper>

    //    запрос на получение детальной информации о продукте
    @GET("productdetails")
    suspend fun getDetailsInfo(): ArrayList<Product>
}