package com.skillbox.skillbox.testonlineshop.classes

import com.google.gson.annotations.SerializedName

data class HotSales(
    @SerializedName("id")
    val id: String,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("is_buy")
    val isBuy: Boolean
)
