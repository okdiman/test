package com.skillbox.skillbox.networking.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val year: String,
    val type: String,
    val id: String,
    val poster: String
) : Parcelable