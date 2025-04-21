package org.example.presentation

import org.example.logic.usecases.GetIraqiMealsUseCase
import org.example.utils.display

class IraqiMealsUI(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) {
    operator fun invoke() {
        println("Finding Iraqi meals ...")
        val meals = getIraqiMealsUseCase.getIraqiMeals()
        println("Your order is ready: ")
        if (meals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            meals.display()
            println("Total number of meals: ${meals.size}")
        }
    }
}