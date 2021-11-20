package com.skillbox.skillbox.cartscreen.data.network

import com.skillbox.skillbox.cartscreen.data.models.CartDetailsWrapper
import retrofit2.http.GET

interface CartApi {
    //    запрос на получение информации о корзине пользователя
    @GET("mycart")
    suspend fun getCartInfo(): ArrayList<CartDetailsWrapper>
}