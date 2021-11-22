package com.skillbox.skillbox.mainscreen.domain.repository

import com.skillbox.skillbox.mainscreen.data.models.CartDetailsWrapper
import com.skillbox.skillbox.mainscreen.data.models.MainScreenResponseWrapper

interface MainScreenRepository {
    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper
    //    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}