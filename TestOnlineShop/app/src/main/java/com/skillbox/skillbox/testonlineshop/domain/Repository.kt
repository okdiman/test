package com.skillbox.skillbox.testonlineshop.domain

import com.skillbox.skillbox.testonlineshop.domain.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.domain.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.domain.models.Product

interface Repository {
    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper
    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product
    //    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}