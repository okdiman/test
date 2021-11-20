package com.skillbox.skillbox.detailsscreen.data.network

import com.skillbox.skillbox.detailsscreen.domain.entities.Product
import retrofit2.http.GET

interface DetailApi {
    //    запрос на получение детальной информации о продукте
    @GET("productdetails")
    suspend fun getDetailsInfo(): ArrayList<Product>
}