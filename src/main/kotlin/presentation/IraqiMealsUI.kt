package org.example.presentation

import org.example.logic.usecases.GetIraqiMealsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class IraqiMealsUI(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Finding Iraqi meals ...")
        val meals = getIraqiMealsUseCase.getIraqiMeals()
        write("Your order is ready: ")
        if (meals.isEmpty()) {
            write("No meals found matching the criteria.")
        } else {
            meals.display()
            write("Total number of meals: ${meals.size}")
        }
    }
}