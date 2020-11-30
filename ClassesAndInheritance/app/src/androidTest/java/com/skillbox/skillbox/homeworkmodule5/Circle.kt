package com.skillbox.skillbox.homeworkmodule5

class Circle(
        radius: Int
): Shape {
    var radius: Int by PrintAreaOnChangeDelegate(radius)
    override val name: String = "Circle"

    override fun calculateArea(): Double {
        return Math.PI * radius * radius
    }
}