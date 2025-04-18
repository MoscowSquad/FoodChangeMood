package org.example

import org.example.data.MockDataRepository
import org.example.logic.userInputGuess

fun main() {
    try {
        // Get the repository instance
        val mealRepository = MockDataRepository()

        // Get a random meal with all its details
        val randomMeal = mealRepository.getAllMeals().random()

        // Extract the name and actual preparation time
        val foodName = randomMeal.name
        val preparationTime = randomMeal.minutes

        println("Guess details about: $foodName")
        // Start the guessing game with both values
        userInputGuess().apply {
            guessPreparationTime(foodName, preparationTime)
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
    }
}