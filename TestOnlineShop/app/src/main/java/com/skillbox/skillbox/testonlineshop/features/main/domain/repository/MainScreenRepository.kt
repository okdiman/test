package com.skillbox.skillbox.testonlineshop.features.main.domain.repository

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper

interface MainScreenRepository {
    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper
    //    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}