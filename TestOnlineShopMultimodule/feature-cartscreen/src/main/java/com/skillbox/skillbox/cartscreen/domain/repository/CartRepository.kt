package com.skillbox.skillbox.cartscreen.domain.repository

import com.skillbox.skillbox.cartscreen.data.models.CartDetailsWrapper

interface CartRepository {
//    запрос на получение информации о корзине пользователя
    suspend fun getCartInfo(): CartDetailsWrapper
}