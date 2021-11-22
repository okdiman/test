package com.skillbox.skillbox.detailsscreen.domain.repository

import com.skillbox.skillbox.detailsscreen.domain.entities.Product

interface DetailRepository {
    //    запрос на получение детальной информации о продукте
    suspend fun getDetailsInfo(): Product
}