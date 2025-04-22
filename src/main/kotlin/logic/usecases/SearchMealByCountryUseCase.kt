package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class SearchMealByCountryUseCase(
    private val repository: MealRepository
) {
    fun searchMealsByCountry(country: String): List<Meal> {
        return repository.getAllMeals()
            .filter { meal -> meal.matchesCountry(country) }
            .shuffled()
            .take(MAX_MEALS)
    }

    private fun Meal.matchesCountry(country: String): Boolean{
        val lowerCountry = country.lowercase()
        return tags?.any{ it.contains(lowerCountry , ignoreCase = true) } == true ||
                name?.contains(lowerCountry , ignoreCase = true) == true ||
                description?.contains(lowerCountry , ignoreCase = true) == true
    }

    companion object {
        const val MAX_MEALS = 20
    }
}

// 1. Extract the logic inside the filter to another function
// 2. Use random indices instead of shuffled
// 3. Use the exception you have implemented if the list is empty