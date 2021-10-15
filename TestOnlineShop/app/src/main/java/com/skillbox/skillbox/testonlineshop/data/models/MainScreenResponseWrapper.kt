package com.skillbox.skillbox.testonlineshop.data.models

import com.google.gson.annotations.SerializedName

data class MainScreenResponseWrapper(
    @SerializedName("_id")
        val id: String,
    @SerializedName("home_store")
        val homeStore: List<Product>,
    @SerializedName("best_seller")
        val bestSellers: List<Product>
)