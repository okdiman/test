package com.skillbox.skillbox.location

import org.threeten.bp.Instant

data class PointOfLocation(
    val id: Long,
    val lat: Double,
    val lng: Double,
    val alt: Double,
    val speed: Float,
    val accuracy: Float,
    var pointOfTime: Instant,
    val picture: String
) {
}