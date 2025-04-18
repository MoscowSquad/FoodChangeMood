package org.example

import org.example.data.MockDataRepository

fun main() {

    val mock= MockDataRepository()
     mock.getIraqiMeals().forEach {
         println("\n\n Display Iraqi Menu Meals Please :)")

         println("the common meals in iraq is ${it.name}")
     }

}