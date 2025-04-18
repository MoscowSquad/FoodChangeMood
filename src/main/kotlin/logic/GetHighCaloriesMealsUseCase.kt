package org.example.logic

import org.example.exceptions.Exceptions
import org.example.model.Meal

const val HIGH_CALORIE_THRESHOLD = 700

class GetHighCaloriesMealsUseCase(private val repository: MealRepository) {
    private val suggestedList = mutableSetOf<Meal>()
    fun invoke(): Meal {
        repository.getAllMeals()
            .filter(::byHighCalories)
            .forEach { meal ->
                if (!suggestedList.contains(meal)) {
                    suggestedList.add(meal)
                    return meal
                }
            }

        throw Exceptions.MealNotFoundException()
    }

    private fun byHighCalories(meal: Meal): Boolean {
        if (meal.nutrition?.calories == null)
            return false

        return (meal.nutrition.calories ?: 0.0) > HIGH_CALORIE_THRESHOLD
    }
}