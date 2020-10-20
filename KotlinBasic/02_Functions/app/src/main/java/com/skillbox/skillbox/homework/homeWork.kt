package com.skillbox.skillbox.homework

import kotlin.math.sqrt

fun main(){
    val solutionSum = solveEquation (a = 4,b = 6,c = 1)
    println(solutionSum)
}

fun solveEquation (a: Int,b: Int,c: Int): Any {
    //расчет дискриминанта
    val d: Int = (b * b - 4 * a * c)
    //расчет корней
    val x1 = (-b + sqrt(d.toDouble())) / (2 * a)
    val x2 = (-b - sqrt(d.toDouble())) / (2 * a)
    println(x1)
    println(x2)
    return if (d > 0) x1 + x2 else "no solutions"
}