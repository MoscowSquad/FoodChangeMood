package org.example.presentation

import org.example.logic.usecases.GetHighCaloriesMealsUseCase
import org.example.model.Exceptions
import org.example.utils.display

class HighCaloriesMealsUI(
    private val getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase
) {
    operator fun invoke() {
        println("Finding high calories meals...")
        try {
            println("Your order is ready: ")
            getHighCaloriesMealsUseCase.nextMeal().also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            println(e.message)
        }
    }
}
