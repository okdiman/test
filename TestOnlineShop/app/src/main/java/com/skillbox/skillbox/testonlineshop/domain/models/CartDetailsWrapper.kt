package com.skillbox.skillbox.testonlineshop.domain.models

import com.google.gson.annotations.SerializedName

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
