package com.skillbox.skillbox.location.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant

@Parcelize
data class PointOfLocation(
    val id: Long,
    val lat: Double,
    val lng: Double,
    val alt: Double,
    val speed: Float,
    val accuracy: Float,
    var pointOfTime: Instant,
    val picture: String
): Parcelable