package org.example.presentation
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Meal
import org.example.presentation.io.ConsoleIO



class GuessGameUI(
    private val randomMealNameUseCase: RandomMealNameProviderUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        try {
            consoleIO.write("Prepare a meal to guess by you ...")
            val meal = randomMealNameUseCase.getRandomMeal()
            consoleIO.write("Guess the preparation time for (${meal.name}): ")
            guessGame(meal)
        } catch (e: Exception) {
            consoleIO.write("Error: ${e.message}")
        }
    }
    fun guessGame(meal: Meal, time: Int = 0) {
        if (time == 3) {
            consoleIO.write("later")
            return
        }
        consoleIO.write("Preparation time:")
        val suggestion = try {
            consoleIO.read()
        } catch (e: IllegalStateException) {
            consoleIO.write("Error: No input provided")
            return
        }
        try {
            if (randomMealNameUseCase.isSuggestRight(suggestion.toIntOrNull())) {
                consoleIO.write("You are correct")
                return
            } else {
                consoleIO.write("Not correct. Guess again, ")
                guessGame(meal, time + 1)
            }
        } catch (e: Exception) {
            consoleIO.write("Invalid input. Please enter a number.")
            guessGame(meal, time) // Retry without incrementing attempts
        }
    }
}