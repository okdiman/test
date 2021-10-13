package com.skillbox.skillbox.testonlineshop.fragments

import com.skillbox.skillbox.testonlineshop.classes.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.network.Network

class MainFragmentRepository {

    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper {
        val list = Network.api.getMainData()
        return list[0]
    }
}