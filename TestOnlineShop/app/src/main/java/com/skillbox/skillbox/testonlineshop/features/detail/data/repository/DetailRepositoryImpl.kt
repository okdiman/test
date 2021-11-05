package com.skillbox.skillbox.testonlineshop.features.detail.data.repository

import com.skillbox.skillbox.testonlineshop.features.detail.data.network.DetailApi
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
import com.skillbox.skillbox.testonlineshop.features.detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val detailApi: DetailApi) :
    DetailRepository {
    //    запрос на получение детальной информации о продукте
    override suspend fun getDetailsInfo(): Product {
        val product = detailApi.getDetailsInfo()
        return product[0]
    }
}