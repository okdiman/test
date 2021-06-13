package com.skillbox.skillbox.location

import org.threeten.bp.Instant

data class PointOfLocation(
    val id: Long,
    val pointOfTime: Instant,
    val picture: String,
    val lat: Int,
    val lng: Int,
    val alt: Int,
    val speed: Int,
    val accuracy: Int
) {
}