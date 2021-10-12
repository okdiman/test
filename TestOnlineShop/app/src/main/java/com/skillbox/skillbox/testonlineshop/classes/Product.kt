package com.skillbox.skillbox.testonlineshop.classes

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("_id")
    val id: String,
    @SerializedName("images")
    val images: String,
    @SerializedName("is_favorites")
    val is_favorites: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("CPU")
    val cpu: String,
    @SerializedName("camera")
    val camera: String,
    @SerializedName("ssd")
    val ssd: String,
    @SerializedName("sd")
    val sd: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("capacity")
    val capacity: String,
    @SerializedName("price")
    val price: Int
) : Parcelable