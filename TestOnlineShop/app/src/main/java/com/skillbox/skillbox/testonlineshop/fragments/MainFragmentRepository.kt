package com.skillbox.skillbox.testonlineshop.fragments

import com.skillbox.skillbox.testonlineshop.classes.MainScreenRequest
import com.skillbox.skillbox.testonlineshop.network.Network

class MainFragmentRepository {
    suspend fun getAllProducts(): MainScreenRequest {
        val list = Network.api.getMainData()
        return list[0]
    }
}