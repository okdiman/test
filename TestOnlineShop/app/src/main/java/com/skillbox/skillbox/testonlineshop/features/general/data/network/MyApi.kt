package com.skillbox.skillbox.testonlineshop.features.general.data.network

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
import retrofit2.http.GET

interface MyApi {
    //    запрос на получение данных для главной страницы
    @GET("main")
    suspend fun getMainData(): ArrayList<MainScreenResponseWrapper>

    //    запрос на получение детальной информации о продукте
    @GET("productdetails")
    suspend fun getDetailsInfo(): ArrayList<Product>

    //    запрос на получение информации о корзине пользователя
    @GET("mycart")
    suspend fun getCartInfo(): ArrayList<CartDetailsWrapper>
}