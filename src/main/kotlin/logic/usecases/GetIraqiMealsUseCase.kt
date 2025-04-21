package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class GetIraqiMealsUseCase(
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