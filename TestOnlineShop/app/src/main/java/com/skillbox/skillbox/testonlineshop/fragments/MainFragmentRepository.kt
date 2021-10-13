package com.skillbox.skillbox.testonlineshop.fragments

import com.skillbox.skillbox.testonlineshop.classes.MainScreenResponseWrapper
import com.skillbox.skillbox.testonlineshop.network.Network

class MainFragmentRepository {
    suspend fun getAllProducts(): MainScreenResponseWrapper {
        val list = Network.api.getMainData()
        return list[0]
    }
}