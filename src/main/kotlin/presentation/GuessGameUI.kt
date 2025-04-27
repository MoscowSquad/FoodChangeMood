package org.example.presentation

import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Meal
import org.example.presentation.io.ConsoleIO


class GuessGameUI(
    private val randomMealNameUseCase: RandomMealNameProviderUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        try {
            write("Prepare a meal to guess by you ...")
            val meal = randomMealNameUseCase.getRandomMeal()
            write("Guess the preparation time for (${meal.name}): ")
            guessGame(meal)
        } catch (e: Exception) {
            write("Error: ${e.message}")
        }
    }

    fun guessGame(meal: Meal, time: Int = 0) {
        if (time == 3) {
            write("later")
            return
        }
        write("Preparation time:")
        val suggestion = try {
            read()
        } catch (_: IllegalStateException) {
            write("Error: No input provided")
            return
        }
        try {
            if (randomMealNameUseCase.isSuggestRight(suggestion.toIntOrNull())) {
                write("You are correct")
                return
            } else {
                write("Not correct. Guess again, ")
                guessGame(meal, time + 1)
            }
        } catch (_: Exception) {
            write("Invalid input. Please enter a number.")
            guessGame(meal, time) // Retry without incrementing attempts
        }
    }
}