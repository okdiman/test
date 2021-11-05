package com.skillbox.skillbox.testonlineshop.features.detail.data.network

import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
import retrofit2.http.GET

interface DetailApi {
    //    запрос на получение детальной информации о продукте
    @GET("productdetails")
    suspend fun getDetailsInfo(): ArrayList<Product>
}