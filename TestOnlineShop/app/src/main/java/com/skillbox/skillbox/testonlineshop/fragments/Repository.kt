package com.skillbox.skillbox.testonlineshop.fragments

import android.util.Log
import com.skillbox.skillbox.testonlineshop.classes.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.network.Network

class Repository {

    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper {
        val list = Network.api.getMainData()
        return list[0]
    }

    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product {
        val product = Network.api.getDetailsInfo()
        Log.i("productDetails", "$product")
        return product[0]
    }
}