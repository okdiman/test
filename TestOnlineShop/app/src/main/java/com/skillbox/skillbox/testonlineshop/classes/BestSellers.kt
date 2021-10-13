package com.skillbox.skillbox.testonlineshop.classes

import com.google.gson.annotations.SerializedName

data class BestSellers(
    @SerializedName("id")
    val id: String,
    @SerializedName("is_favorites")
    val is_favorites: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("price_without_discount")
    val oldPrice: Int,
    @SerializedName("discount_price")
    val newPrice: Int,
    @SerializedName("picture")
    val picture: String
)
