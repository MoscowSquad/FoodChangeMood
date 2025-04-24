package org.example.presentation

import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding easy meal to prepare ...")
        val meals = easyFoodSuggestionUseCase.suggestTenRandomMeals()
        consoleIO.write("Your order is ready: ")
        if (meals.isEmpty()) {
            consoleIO.write("No meals found matching the criteria.")
        } else {
            meals.display()
            consoleIO.write("Total number of meals: ${meals.size}")
        }
    }
}