package org.example.logic

import org.example.model.Meal

class GetIraqiMeals(
    private val repository: MealRepository
) {
    fun getIraqiMeals(): List<Meal> {
        return repository.getAllMeals()
            .filter(::byIraqi)
    }

    private fun byIraqi(meal: Meal): Boolean {
        return meal.tags?.any { it.equals("iraqi", ignoreCase = true) } ?: false ||
                meal.description?.contains("iraq", ignoreCase = true) ?: false
    }
}