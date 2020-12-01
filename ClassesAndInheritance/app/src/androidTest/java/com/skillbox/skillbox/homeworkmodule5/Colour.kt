package com.skillbox.skillbox.homeworkmodule5

enum class Colour(
        val hex: String
) : Drawable {
    WHITE("#fff"),
    BLACK("#000"),
    RED("#f00") {
        override fun draw() {
            println("draw red colour")
        }
    },
    BLUE("#00f");

    fun someMethod() {}

    override fun draw() {
        println("draw colour")
    }

    companion object {
        fun fromName(name: String): Colour? {
            return values().find { it.name == name.toUpperCase() }
        }
    }
}