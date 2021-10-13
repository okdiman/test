package com.skillbox.skillbox.testonlineshop.classes

import com.google.gson.annotations.SerializedName

data class MainScreenRequest(
        @SerializedName("_id")
        val id: String,
        @SerializedName("home_store")
        val homeStore: List<Product>,
        @SerializedName("best_seller")
        val bestSellers: List<Product>
)