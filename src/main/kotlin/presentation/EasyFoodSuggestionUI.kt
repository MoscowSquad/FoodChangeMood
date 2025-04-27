package org.example.presentation

import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Finding easy meal to prepare ...")
        val meals = easyFoodSuggestionUseCase.suggestTenRandomMeals()
        write("Your order is ready: ")
        if (meals.isEmpty()) {
            write("No meals found matching the criteria.")
        } else {
            meals.display()
            write("Total number of meals: ${meals.size}")
        }
    }
}