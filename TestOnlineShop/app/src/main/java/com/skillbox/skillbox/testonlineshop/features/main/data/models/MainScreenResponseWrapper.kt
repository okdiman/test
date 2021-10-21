package com.skillbox.skillbox.testonlineshop.features.main.data.models

import com.google.gson.annotations.SerializedName
import com.skillbox.skillbox.testonlineshop.features.main.domain.entities.BestSellers
import com.skillbox.skillbox.testonlineshop.features.main.domain.entities.HotSales

data class MainScreenResponseWrapper(
    @SerializedName("_id")
        val id: String,
    @SerializedName("home_store")
        val homeStore: List<HotSales>,
    @SerializedName("best_seller")
        val bestSellers: List<BestSellers>
)