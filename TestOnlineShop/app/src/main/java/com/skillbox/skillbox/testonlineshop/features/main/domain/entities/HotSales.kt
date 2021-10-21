package com.skillbox.skillbox.testonlineshop.features.main.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotSales(
    @SerializedName("_id")
    val id: String,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("is_favorites")
    var is_favorites: Boolean = false,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("is_buy")
    val isBuy: Boolean = false
): Parcelable
