package org.example

import org.example.utils.CustomParser

fun main() {
    println("Hello World!")
    mainParsingTest()
}


fun mainParsingTest(){
    val parser = CustomParser()
    val file = parser.getResourceFile("csvTest.csv")
    val list = parser.parseMealsCsv(file)
    println(list.size)
    val sh = list.shuffled()
    sh.take(5).forEach { item ->
        println(item.toString())
    }
}


