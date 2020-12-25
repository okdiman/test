package com.skillbox.skillbox.homework3

fun main() {
    val immutableSet = setOf(1, 2, 8, 1, 4, 6, 5, 2, 5)
    println(immutableSet)

    val unionSet = setOf(1, 2, 3).union(setOf(1, 4, 5))
    println(unionSet)

    val substrectSet = setOf(1, 2, 3).subtract(setOf(1, 2, 5))
    println(substrectSet)

    val intersect = setOf(1, 2, 4).intersect(setOf(4, 5, 6))
    println(intersect)

    val sortedSet = sortedSetOf(1, 5, -2, 7, 1)
    println(sortedSet.descendingSet())

    mutableSetOf(1, 2, 3).add(34)

    val hashSet = hashSetOf(1, 2, 3)
    hashSet.add(5)
    hashSet.remove(1)
    hashSet.contains(3)
    println(3 in hashSet)
}
