package com.skillbox.skillbox.testonlineshop.data

import com.skillbox.skillbox.testonlineshop.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.data.models.Product
import com.skillbox.skillbox.testonlineshop.data.network.Network

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


}