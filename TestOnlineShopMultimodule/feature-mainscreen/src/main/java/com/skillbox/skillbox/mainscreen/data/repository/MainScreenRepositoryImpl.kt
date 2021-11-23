package com.skillbox.skillbox.mainscreen.data.repository

import com.skillbox.skillbox.mainscreen.data.models.CartDetailsWrapper
import com.skillbox.skillbox.mainscreen.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.mainscreen.data.network.MainScreenApi
import com.skillbox.skillbox.mainscreen.domain.repository.MainScreenRepository

class MainScreenRepositoryImpl (private val mainScreenApi: MainScreenApi) :
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