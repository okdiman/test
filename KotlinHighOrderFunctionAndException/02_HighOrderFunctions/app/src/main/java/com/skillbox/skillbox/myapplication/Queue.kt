package com.skillbox.skillbox.myapplication

class Queue<T>() {
    private val queueList = mutableListOf<T>()
    fun enqueue(item: T) {
        queueList.add(item)
    }

    fun dequeue(): T? {
        return if (queueList.isEmpty()) null
        else queueList[0]
    }

    fun filterNewQueue(element: (T) -> Boolean): Queue<T> {
        val newQueue = Queue<T>()
        queueList.forEach {
            if (element(it)) newQueue.enqueue(it)
        }
        return newQueue
    }

    override fun toString(): String {
        return "queue: $queueList"
    }
}