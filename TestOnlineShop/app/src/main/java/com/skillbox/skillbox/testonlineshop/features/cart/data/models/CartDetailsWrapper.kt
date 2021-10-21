package com.skillbox.skillbox.testonlineshop.features.cart.data.models

import com.google.gson.annotations.SerializedName
import com.skillbox.skillbox.testonlineshop.features.cart.domain.entities.Basket

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
