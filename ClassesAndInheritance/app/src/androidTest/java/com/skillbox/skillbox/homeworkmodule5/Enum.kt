package com.skillbox.skillbox.homeworkmodule5

fun main(){
    val colour = Colour.BLACK.hex
    Colour.RED.draw()
    Colour.values().forEach {
        it.draw()
    }
    Colour.fromName("#00f")

    SealedColour.black
    SealedColour.customColour("#ff0")
}

fun invertColour(colour: Colour): Colour{
    return when (colour){
        Colour.WHITE -> Colour.BLACK
        Colour.BLACK -> Colour.WHITE
        Colour.RED -> Colour.BLUE
        Colour.BLUE -> Colour.RED
    }
}

fun invertSealedColour(colour: SealedColour): SealedColour{
    return when (colour){
        SealedColour.black -> SealedColour.white
        SealedColour.white -> SealedColour.black
        is SealedColour.customColour -> SealedColour.white
    }
}