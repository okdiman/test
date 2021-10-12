package com.skillbox.skillbox.testonlineshop.network

import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.classes.ProductRequestResults
import retrofit2.http.GET

interface MyApi {
    @GET("main")
    suspend fun getProducts():ProductRequestResults?
}