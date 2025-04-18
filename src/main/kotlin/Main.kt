package org.example

import org.example.utils.CustomParser

fun main() {
    println("Hello World!")
}


fun mainParsingTest(){
    val parser = CustomParser()
    val file = parser.getResourceFile("food.csv")
    val list = parser.parseMealsCsv(file)
    list.take(10).forEach { item ->
        println(item.toString())
    }
}