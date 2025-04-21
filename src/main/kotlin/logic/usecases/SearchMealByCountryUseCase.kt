package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class SearchMealByCountryUseCase(
    private val repository: MealRepository
) {
    fun searchMealsByCountry(country: String): List<Meal> {
        return repository.getAllMeals()
            .filter { meal ->
                val lowerCountry = country.lowercase()
                meal.tags?.any { it.contains(lowerCountry) } ?: false
                        meal.name?.contains(lowerCountry, ignoreCase = true) ?: false
                        meal.description?.contains(lowerCountry, ignoreCase = true) == true
            }
            .shuffled()
            .take(20)
    }
}

// 1. Extract the logic inside the filter to another function
// 2. Use random indices instead of shuffled
// 3. Use the exception you have implemented if the list is empty