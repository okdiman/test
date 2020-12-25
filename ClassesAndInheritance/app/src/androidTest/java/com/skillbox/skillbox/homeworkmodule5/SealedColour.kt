package com.skillbox.skillbox.homeworkmodule5

sealed class SealedColour(
    val hex: String
) : Drawable {
    object white : SealedColour("#fff")
    object black : SealedColour("#000") {
        override fun draw() {
            println("draw black")
        }
    }

    class customColour(hex: String) : SealedColour(hex)

    override fun draw() {
        println("draw sealed")
    }
}
