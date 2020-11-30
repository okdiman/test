package com.skillbox.skillbox.homeworkmodule5

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    val rectangle = Rectangle(0, 0, 15, 25)
    rectangle.width = 25
    rectangle.heigth = 35
    val circle = Circle(15)
    circle.radius = 12
}

class PrintAreaOnChangeDelegate<T>(
        private var value: T
) : ReadWriteProperty<Shape, T> {
    override fun setValue(thisRef: Shape, property: KProperty<*>, value: T) {
        println("before change property ${property.name} = ${thisRef.calculateArea()}")
        this.value = value
        println("after change property ${property.name} = ${thisRef.calculateArea()}")
    }

    override fun getValue(thisRef: Shape, property: KProperty<*>): T = value
}