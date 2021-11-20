package com.skillbox.skillbox.detailsscreen.domain.entities

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
    val price: Int = 0
) : Parcelable