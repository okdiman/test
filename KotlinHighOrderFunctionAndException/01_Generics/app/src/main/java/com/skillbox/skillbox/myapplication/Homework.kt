package com.skillbox.skillbox.myapplication

fun main(){

}

fun <T:Number> genericFunction (list: MutableList<T>){
    println(list.filter { it % 2 == 0 })
    println(list.filter {it is Double})
}
class Queue <T> (item: T){
    fun enqueue (item: T){

    }
    fun dequeue () : T{
        return
    }
}