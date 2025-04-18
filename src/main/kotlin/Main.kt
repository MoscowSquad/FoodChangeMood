package org.example

import org.example.data.MockDataRepository
import org.example.logic.userInputGuessImpl

fun main() {
    try {
        val mealRepository = MockDataRepository()
        val randomMeal = mealRepository.getAllMeals().random()
        val foodName = randomMeal.name
        val preparationTime = randomMeal.minutes

        println("Guess details about: $foodName")
        userInputGuessImpl().apply {
            guessPreparationTime(foodName, preparationTime)
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
    }
}