package org.example.logic

import org.example.model.Exceptions
import org.example.model.Meal

class GetMealsByDateUseCase(
    private val repository: MealRepository
) {
    fun getMealsByDate(date: String): List<Meal> {
        val matchedMeals = repository.getAllMeals().filter { it.submitted == date }
        if (matchedMeals.isEmpty()) throw Exceptions.NoMealsFound("No meals found on this date.")
        return matchedMeals
    }
}