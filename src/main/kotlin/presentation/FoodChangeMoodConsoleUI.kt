package org.example.presentation

import org.example.logic.MealRepository

class FoodChangeMoodConsoleUI(
    private val mealRepository: MealRepository
) {
    fun exploreFoodCulture() {
        print("Enter a country name to explore its food culture: ")
        val country = readln()
        val meals = mealRepository.searchMealsByCountry(country)

        if (meals.isEmpty()) {
            println("No meals found for $country")
        } else {
            println("Found ${meals.size} meals related to $country:")
            meals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name}")
            }
        }
    }
}