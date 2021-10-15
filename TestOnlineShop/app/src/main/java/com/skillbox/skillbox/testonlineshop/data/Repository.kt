package com.skillbox.skillbox.testonlineshop.data

import com.skillbox.skillbox.testonlineshop.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.data.models.Product

interface Repository {
    //    запрос на получение данных неохоимых для стартового экрана
    suspend fun getMainScreenData(): MainScreenResponseWrapper
    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product
}