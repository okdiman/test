package com.skillbox.skillbox.testonlineshop.features.general.domain.repository

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product

interface Repository {
    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper
    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product
    //    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}