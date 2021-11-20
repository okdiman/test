package com.skillbox.skillbox.mainscreen.data.network


import com.skillbox.skillbox.mainscreen.data.models.CartDetailsWrapper
import com.skillbox.skillbox.mainscreen.data.models.MainScreenResponseWrapper
import retrofit2.http.GET

interface MainScreenApi {
    //    запрос на получение данных для главной страницы
    @GET("main")
    suspend fun getMainData(): ArrayList<MainScreenResponseWrapper>

    //    запрос на получение информации о корзине пользователя
    @GET("mycart")
    suspend fun getCartInfo(): ArrayList<CartDetailsWrapper>
}