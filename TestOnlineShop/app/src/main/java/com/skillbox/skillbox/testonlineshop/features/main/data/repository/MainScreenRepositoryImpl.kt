package com.skillbox.skillbox.testonlineshop.features.main.data.repository

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.network.MainScreenApi
import com.skillbox.skillbox.testonlineshop.features.main.domain.repository.MainScreenRepository
import javax.inject.Inject

class MainScreenRepositoryImpl @Inject constructor(private val mainScreenApi: MainScreenApi) :
    MainScreenRepository {
    //    запрос на получение данных неохоимых для стартового экрана
    override suspend fun getMainScreenData(): MainScreenResponseWrapper {
        val list = mainScreenApi.getMainData()
        return list[0]
    }

    //    запрос на получение информации о корзине пользователя
    override suspend fun getCartInfo(): CartDetailsWrapper {
        val list = mainScreenApi.getCartInfo()
        return list[0]
    }
}