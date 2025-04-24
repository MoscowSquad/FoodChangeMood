package org.example.presentation

import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Meal
import org.example.presentation.io.ConsoleIO

class GuessGameUI(
    private val randomMealNameUseCase: RandomMealNameProviderUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Prepare a meal to guess by you ...")
        val meal = randomMealNameUseCase.getRandomMeal()
        consoleIO.write("Guess the preparation time for (${meal.name}): ")
        guessGame(meal)
    }

    private fun guessGame(meal: Meal, time: Int = 0) {
        if (time == 3) {
            consoleIO.write("later")
            return
        }

        print("Preparation time:")
        val suggestion = readln()
        if (meal.minutes == suggestion.toIntOrNull()) {
            consoleIO.write("You are correct")
            return
        } else {
            consoleIO.write("Not correct. Guess again, ")
            guessGame(meal, time + 1)
        }
    }
}
