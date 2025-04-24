package org.example.presentation

import org.example.logic.usecases.GetIraqiMealsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class IraqiMealsUI(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding Iraqi meals ...")
        val meals = getIraqiMealsUseCase.getIraqiMeals()
        consoleIO.write("Your order is ready: ")
        if (meals.isEmpty()) {
            consoleIO.write("No meals found matching the criteria.")
        } else {
            meals.display()
            consoleIO.write("Total number of meals: ${meals.size}")
        }
    }
}