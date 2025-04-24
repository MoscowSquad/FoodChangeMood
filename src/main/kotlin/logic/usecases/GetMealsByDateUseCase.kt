package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetMealsByDateUseCase(
    private val repository: MealRepository
) {
    fun getMealsByDate(date: String): List<Meal> {
        val matchedMeals = repository.getAllMeals().filter { currentMeal ->
            currentMeal.submitted == date
        }.takeIf { it.isNotEmpty() }
            ?: throw Exceptions.NoMealsFoundException("No meals found on this date.")

        return matchedMeals
    }
}


