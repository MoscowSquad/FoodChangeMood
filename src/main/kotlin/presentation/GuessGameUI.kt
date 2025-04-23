package org.example.presentation

import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Meal

class GuessGameUI(
    private val randomMealNameUseCase: RandomMealNameProviderUseCase
) {
    operator fun invoke() {
        println("Prepare a meal to guess by you ...")
        val meal = randomMealNameUseCase.getRandomMeal()
        println("Guess the preparation time for (${meal.name}): ")
        guessGame(meal)
    }

    private fun guessGame(meal: Meal, time: Int = 0) {
        if (time == 3) {
            println("later")
            return
        }

        print("Preparation time:")
        val suggestion = readln()
        if (meal.minutes == suggestion.toIntOrNull()) {
            println("You are correct")
            return
        } else {
            print("Not correct. Guess again, ")
            guessGame(meal, time + 1)
        }
    }
}
