package org.example.presentation

import org.example.logic.usecases.SearchMealByCountryUseCase
import org.example.presentation.io.ConsoleIO

class SearchMealByCountryUI(
    private val searchMealByCountryUseCase: SearchMealByCountryUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        print("Enter a country name to explore its food culture: ")
        val country = readln()
        val meals = searchMealByCountryUseCase.searchMealsByCountry(country)

        if (meals.isEmpty()) {
            consoleIO.write("No meals found for $country")
        } else {
            consoleIO.write("Found ${meals.size} meals related to $country:")
            meals.forEachIndexed { index, meal ->
                consoleIO.write("${index + 1}. ${meal.name}")
            }
        }
    }
}
