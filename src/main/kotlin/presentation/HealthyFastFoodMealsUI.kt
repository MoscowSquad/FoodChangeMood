package org.example.presentation

import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.utils.display

class HealthyFastFoodMealsUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase
) {
    operator fun invoke() {
        println("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        println("Your order is ready: ")
        if (healthyMeals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            healthyMeals.display()
            println("Total number of meals: ${healthyMeals.size}")
        }
    }
}