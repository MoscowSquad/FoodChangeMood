package org.example.presentation

import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.utils.display

class KetoDietMealHelperUI(
    private val getKetoDietMealUseCase: GetKetoDietMealUseCase
) {
    operator fun invoke() {
        println("Welcome to the Keto Diet Meal Helper!")

        while (true) {
            try {
                println("\nFinding a new Keto-friendly meal suggestion...")
                val meal = getKetoDietMealUseCase.getKetoMeal()
                println("Suggested meal: ${meal.name}")

                println("\nWhat would you like to do?")
                println("[1] Like")
                println("[2] Dislike (Show another meal)")
                println("[3] Exit")

                when (readlnOrNull()?.trim()) {
                    "1" -> {
                        val likedMeal = getKetoDietMealUseCase.likeMeal()
                        println("You liked this meal! Here's the full detail again:")
                    }
                    "2" -> {
                        println("Finding a new suggestion...")
                        continue
                    }
                    "3" -> {
                        println("Thanks for using Keto Diet Meal Helper. Stay healthy!")
                        break
                    }
                    else -> println("Invalid option. Please select 1, 2, or 3.")
                }
                meal.display()

            } catch (e: Exceptions.MealNotFoundException) {
                println("${e.message}")
                break
            }
        }
    }
}
