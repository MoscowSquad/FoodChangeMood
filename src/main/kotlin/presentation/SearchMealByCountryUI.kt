package org.example.presentation

import org.example.logic.usecases.SearchMealByCountryUseCase
import org.example.presentation.io.ConsoleIO

class SearchMealByCountryUI(
    private val searchMealByCountryUseCase: SearchMealByCountryUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        print("Enter a country name to explore its food culture: ")
        val country = read()
        val meals = searchMealByCountryUseCase.searchMealsByCountry(country)

        if (meals.isEmpty()) {
            write("No meals found for $country")
        } else {
            write("Found ${meals.size} meals related to $country:")
            meals.forEachIndexed { index, meal ->
                write("${index + 1}. ${meal.name}")
            }
        }
    }
}
