package com.skillbox.skillbox.testonlineshop.features.detail.domain.repository

import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product

interface DetailRepository {
    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product
}