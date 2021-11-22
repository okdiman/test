package com.skillbox.skillbox.mainscreen.data.models

import com.google.gson.annotations.SerializedName
import com.skillbox.skillbox.mainscreen.domain.entities.Basket

data class CartDetailsWrapper(
    @SerializedName("_id")
    val id: String,
    @SerializedName("basket")
    val basket: ArrayList<Basket>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("Delivery")
    val delivery: String
)
