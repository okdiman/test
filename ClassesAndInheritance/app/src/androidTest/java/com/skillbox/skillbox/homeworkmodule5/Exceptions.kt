package com.skillbox.skillbox.homeworkmodule5

fun main() {
//    try {
//        functionWithExceptions()
//    } catch (t: NullPointerException) {
//        println("catch NullPointerException with info = ${t.message}")
//    } catch (t: Exception) {
//        println("catch exception with info = ${t.message}")
//    } catch (t: Throwable) {
//        println("catch throwable with info = ${t.message}")
//    } finally {
//        println("finally")
//    }
    functionOuter()
}

fun functionOuter() {
    println("functionOuter before")
    functionWithExceptionHandLing()
    println("functionOuter after")
}

fun functionWithExceptionHandLing() {
    println("functionWithExceptionHandLing before")
    try {
        functionInner()
    } catch (t: Throwable) {
        println("catch throwable with info = ${t.message}")
    }
    println("functionWithExceptionHandLing after")
}

fun functionInner() {
    println("functionInner before")
    functionWithExceptions()
    println("functionInner after")
}

fun functionWithExceptions() {
    println("functionWithExceptions before")
    throw Exception("exception with additional info")
    println("functionWithExceptions after")
}