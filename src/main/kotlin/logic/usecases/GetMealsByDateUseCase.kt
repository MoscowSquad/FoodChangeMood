package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetMealsByDateUseCase(
    private val repository: MealRepository
) {
    fun getMealsByDate(date: String): List<Meal> {
        val matchedMeals = repository.getAllMeals().filter { it.submitted == date }
        if (matchedMeals.isEmpty()) throw Exceptions.NoMealsFoundException("No meals found on this date.")
        return matchedMeals
    }
}


// 1. Use random indices instead of shuffled
// 2. Use takeIf instead of if condition