package org.example

fun main() {
    val list = listOf(1,2,3,4,5)
    val list2 = mutableListOf<Int>()
    list.shuffled().take(2).forEach { list2.add(it) }.also { println(list2) }
}