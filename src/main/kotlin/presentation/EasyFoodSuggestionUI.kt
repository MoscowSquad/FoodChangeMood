package org.example.presentation

import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.utils.display

class EasyFoodSuggestionUI(
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
) {
    operator fun invoke() {
        println("Finding easy meal to prepare ...")
        val meals = easyFoodSuggestionUseCase.suggestTenRandomMeals()
        println("Your order is ready: ")
        if (meals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            meals.display()
            println("Total number of meals: ${meals.size}")
        }
    }
}