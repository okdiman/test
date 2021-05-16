package com.skillbox.skillbox.myapplication

enum class ArticlesType(val type: String) {
    ATLANTIC("Atlantic"),
    INDIAN("Indian"),
    PACIFIC("Pacific"),
    SINGLE("Single");

    companion object {
        fun fromInt(value: Int): ArticlesType {
            return values()[value]
        }
    }
}