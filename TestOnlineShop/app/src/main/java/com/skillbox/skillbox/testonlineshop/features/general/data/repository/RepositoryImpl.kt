package com.skillbox.skillbox.testonlineshop.features.general.data.repository

import com.skillbox.skillbox.testonlineshop.features.general.data.network.MyApi
import com.skillbox.skillbox.testonlineshop.features.general.domain.repository.Repository
import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: MyApi) : Repository {

    //    запрос на получение данных неохоимых для стартового экрана
    override suspend fun getMainScreenData(): MainScreenResponseWrapper {
        val list = api.getMainData()
        return list[0]
    }


    //    запрос на получение детальной информации о продукте
    override suspend fun getDetailsInfo(): Product {
        val product = api.getDetailsInfo()
        return product[0]
    }

    //    запрос на получение информации о корзине пользователя
    override suspend fun getCartInfo(): CartDetailsWrapper {
        val list = api.getCartInfo()
        return list[0]
    }
}