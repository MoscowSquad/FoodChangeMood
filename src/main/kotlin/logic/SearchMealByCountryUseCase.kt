package org.example.logic

import org.example.model.Meal

class SearchMealByCountryUseCase(
    private val repository: MealRepository
) {
    fun searchMealsByCountry(country: String): List<Meal> {
        return repository.getAllMeals()
            .filter { meal ->
                val lowerCountry = country.lowercase()
                meal.tags.any { it.contains(lowerCountry) } ||
                        meal.name.contains(lowerCountry, ignoreCase = true) ||
                        meal.description?.contains(lowerCountry, ignoreCase = true) == true
            }
            .shuffled()
            .take(20)
    }
}