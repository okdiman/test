package com.skillbox.skillbox.testonlineshop.features.main.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BestSellers(
    @SerializedName("_id")
    val id: String,
    @SerializedName("is_favorites")
    var is_favorites: Boolean = false,
    @SerializedName("title")
    val title: String,
    @SerializedName("price_without_discount")
    val oldPrice: Int?,
    @SerializedName("discount_price")
    val newPrice: Int?,
    @SerializedName("picture")
    val picture: String?,
) : Parcelable
