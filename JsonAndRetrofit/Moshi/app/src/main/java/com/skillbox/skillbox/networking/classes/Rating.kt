package com.skillbox.skillbox.networking.classes

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Rating : Parcelable {
    @Json(name = "G")
    GENERAL,
    PG,

    @Json(name = "PG-13")
    PG_13,
    R,

    @Json(name = "NC-17")
    NC_17
}