package org.example.presentation

import org.example.logic.usecases.SearchMealByCountryUseCase

class SearchMealByCountryUI(
    private val searchMealByCountryUseCase: SearchMealByCountryUseCase,
) {
    operator fun invoke() {
        print("Enter a country name to explore its food culture: ")
        val country = readln()
        val meals = searchMealByCountryUseCase.searchMealsByCountry(country)

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
