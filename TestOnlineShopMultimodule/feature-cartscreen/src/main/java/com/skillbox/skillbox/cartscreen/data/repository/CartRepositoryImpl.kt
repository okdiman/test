package com.skillbox.skillbox.cartscreen.data.repository

import com.skillbox.skillbox.cartscreen.data.models.CartDetailsWrapper
import com.skillbox.skillbox.cartscreen.data.network.CartApi
import com.skillbox.skillbox.cartscreen.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartApi: CartApi) : CartRepository {
    //    запрос на получение информации о корзине пользователя
    override suspend fun getCartInfo(): CartDetailsWrapper {
        val list = cartApi.getCartInfo()
        return list[0]
    }
}