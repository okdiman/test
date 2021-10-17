package com.skillbox.skillbox.testonlineshop.data

import android.util.Log
import com.skillbox.skillbox.testonlineshop.domain.models.CartDetailsWrapper
import com.skillbox.skillbox.testonlineshop.domain.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.domain.models.Product
import com.skillbox.skillbox.testonlineshop.data.network.Network
import com.skillbox.skillbox.testonlineshop.domain.Repository

class RepositoryImpl : Repository {

    //    запрос на получение данных неохоимых для стартового экрана
    override suspend fun getMainScreenData(): MainScreenResponseWrapper {
        val list = Network.api.getMainData()
        return list[0]
    }

    //    запрос на получение детальной информации о продукте
    override suspend fun getDetailsInfo(): Product {
        val product = Network.api.getDetailsInfo()
        return product[0]
    }
    //    запрос на получение информации о корзине пользователя
    override suspend fun getCartInfo(): CartDetailsWrapper {
        val list = Network.api.getCartInfo()
        return list[0]
    }

}