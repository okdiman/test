package com.skillbox.skillbox.myapplication

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PrintNamePersonAndHisPets(
    private var person: Person,
    private var value: HashSet<Animal>
) : ReadOnlyProperty<Person, HashSet<Animal>> {
    override fun getValue(thisRef: Person, property: KProperty<*>): HashSet<Animal> {
        println("Список животных ${person.name}: $value")
        return value
    }

    fun setValue(thisRef: Person, property: KProperty<*>): String {
        println("Список животных ${person.name}: $value")
        return ""
    }

    override fun toString(): String {
        return "${person.name}, $value"
    }
}