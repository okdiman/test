package com.skillbox.skillbox.testonlineshop.classes

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("_id")
    val id: String,
    @SerializedName("images")
    val images: ArrayList<String>?,
    @SerializedName("is_favorites")
    var is_favorites: Boolean = false,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: Float?,
    @SerializedName("CPU")
    val cpu: String?,
    @SerializedName("camera")
    val camera: String?,
    @SerializedName("ssd")
    val ssd: String?,
    @SerializedName("sd")
    val sd: String?,
    @SerializedName("color")
    val color: ArrayList<String>?,
    @SerializedName("capacity")
    val capacity: ArrayList<String>?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("price_without_discount")
    val oldPrice: Int?,
    @SerializedName("discount_price")
    val newPrice: Int?,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("is_buy")
    val isBuy: Boolean = false
) : Parcelable