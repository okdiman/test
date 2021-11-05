package com.skillbox.skillbox.testonlineshop.features.cart.domain.repository

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper

interface CartRepository {
//    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}