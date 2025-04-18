package org.example

import org.example.data.MockDataRepository

fun main() {

    val mock= MockDataRepository()
     mock.getIraqiMeals().forEach {
     println(it.name)
     }

}