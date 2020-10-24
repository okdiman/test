package com.skillbox.skillbox.homework3

fun main (){
    val pair1 = Pair("key", "Value")
    val pair2 = 1 to 2

    println(pair1.first)
    println(pair1.second)
    println(pair2)

    val map = mapOf(
            "Name 1" to "+79876519982",
            "Name 1" to "+79876519909",
            "Name 2" to "+79880151102",
            "Name 3" to "+79275625675"
    )

    println(map["Name 1"])
    println(map["Name 4"])

    val mutableMap = mutableMapOf(
            "1" to "2",
            "2" to "3",
            "3" to "4"
    )
    mutableMap

    mutableMap ["Name 1"] = "45680"
    mutableMap.put("yiy", "uahsdj")
    mutableMap.remove("1")
    println(mutableMap)

    val sortedMap = sortedMapOf(
            "2" to "22",
            "1" to "11",
            "5" to "55"
    )
    val hashMap = hashMapOf(
            "2" to  "22",
            "1" to  "11",
            "3" to "33"
    )
    println(sortedMap)
    println(hashMap)
    println(hashMap.keys)
}