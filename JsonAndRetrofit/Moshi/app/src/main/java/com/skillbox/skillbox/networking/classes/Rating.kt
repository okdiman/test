package com.skillbox.skillbox.networking.classes

import com.squareup.moshi.Json

enum class Rating {
    @Json(name = "G")
    GENERAL,
    PG,

    @Json(name = "PG-13")
    PG_13,
    R,

    @Json(name = "NC-17")
    NC_17
}