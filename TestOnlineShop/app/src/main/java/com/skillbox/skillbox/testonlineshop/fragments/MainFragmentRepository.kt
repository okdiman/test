package com.skillbox.skillbox.testonlineshop.fragments

import android.util.Log
import com.skillbox.skillbox.testonlineshop.classes.MainScreenRequest
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.network.Network

class MainFragmentRepository {
    suspend fun getAllProducts():MainScreenRequest {
        Log.i("listOfProducts", "start main repo")
        val list = Network.api.getProducts()
        Log.i("listOfProducts", "$list")
        return list[0]
    }
}