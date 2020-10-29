package com.skillbox.skillbox.homeworkmodule5

class Rectangle(
    x:Int,
    y: Int,
    private val width: Int,
    private val heigth: Int
): AbstractShape (x, y), Comparable <Rectangle> {
    override val name: String = "rectungle"

    override fun calculateArea(): Double = width * heigth.toDouble()

    override fun compareTo(other: Rectangle): Int {
        return (width + heigth) - (other.width + other.heigth)
    }

    override fun toString(): String {
        return "Rectangle(width=$width, heigth=$heigth)"
    }


}