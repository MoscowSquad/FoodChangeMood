package org.example.presentation

import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class HealthyFastFoodMealsUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        consoleIO.write("Your order is ready: ")
        if (healthyMeals.isEmpty()) {
            consoleIO.write("No meals found matching the criteria.")
        } else {
            healthyMeals.display()
            consoleIO.write("Total number of meals: ${healthyMeals.size}")
        }
    }
}