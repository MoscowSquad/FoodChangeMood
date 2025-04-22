package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.utils.takeRandomMeals

class SearchMealByCountryUseCase(
    private val repository: MealRepository
) {
    fun searchMealsByCountry(country: String): List<Meal> {
        return repository.getAllMeals()
            .filter { meal -> meal.matchesCountry(country) }
            .takeIf { it.isNotEmpty() }
            ?.takeRandomMeals(MAX_MEALS)
            ?: throw Exceptions.NoMealsFoundException("No Meal Found With Country: $country")
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

