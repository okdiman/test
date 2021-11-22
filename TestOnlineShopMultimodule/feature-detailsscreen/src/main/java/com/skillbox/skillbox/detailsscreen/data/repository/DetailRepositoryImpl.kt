package com.skillbox.skillbox.detailsscreen.data.repository

import com.skillbox.skillbox.detailsscreen.data.network.DetailApi
import com.skillbox.skillbox.detailsscreen.domain.entities.Product
import com.skillbox.skillbox.detailsscreen.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl (private val detailApi: DetailApi) :
    DetailRepository {
    //    запрос на получение детальной информации о продукте
    override suspend fun getDetailsInfo(): Product {
        val product = detailApi.getDetailsInfo()
        return product[0]
    }
}