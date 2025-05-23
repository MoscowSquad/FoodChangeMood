package org.example.presentation

import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class HealthyFastFoodMealsUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        if (healthyMeals.isEmpty()) {
            write("No meals found matching the criteria.")
        } else {
            write("Your order is ready: ")
            healthyMeals.display()
            write("Total number of meals: ${healthyMeals.size}")
        }
    }
}