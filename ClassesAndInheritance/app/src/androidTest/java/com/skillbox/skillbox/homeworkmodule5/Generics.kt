package com.skillbox.skillbox.homeworkmodule5

fun main() {
    val genericObject = GenericClass(1.1)

    val genericObject2 = GenericClass(1.1)
    val genericObject3 = GenericClass(1f)
    val genericObject4 = GenericClass(1f)

    println(sumGeneric(genericObject, genericObject2))
    println(sumGeneric(genericObject3, genericObject4))

    updateContrvariantClass(ContrvariantClass<Any>(465))
}

class GenericClass<out T : Number> (default: T) {
    private var item: T = default

    fun getItem(): T {
        return item
    }
}

class ContrvariantClass <in T> (default: T) {
    private var item: T = default

    fun setItem(newItem: T) {
        item = newItem
    }
}

fun updateContrvariantClass(input: ContrvariantClass<Number>) {
    input.setItem(2.2)
}

fun sumGeneric(a: GenericClass<Number>, b: GenericClass<Number>): Int {
    return a.getItem().toInt() + b.getItem().toInt()
}

fun <T> printGenericObject(item: T) {
    println(item.toString())
}
