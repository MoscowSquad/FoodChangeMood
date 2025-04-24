package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetKetoDietMealUseCase(private val repository: MealRepository) {
    private val suggestedMeals = mutableSetOf<Meal>()
    private var currentMeal: Meal? = null

    // Suggest a keto meal
    fun getKetoMeal(): Meal {
        val available = repository.getAllMeals()
        return available
            .filter { isKetoFriendly(it) && it.notInSuggestedMeals() }
            .randomOrNull()
            ?.also { meal ->
                suggestedMeals.add(meal)
                currentMeal = meal
            }
            ?: throw Exceptions.NoMealsFoundException("No more keto-friendly meals available.")
    }

    // Check if the meal is keto-friendly based on the nutritional info
    private fun Meal.notInSuggestedMeals(): Boolean {
        return suggestedMeals.contains(this).not()
    }

    // Mark the current meal as liked (e.g., show full details)
    fun likeMeal(): Meal {
        return currentMeal ?: throw Exceptions.NoMealsFoundException("No meal is currently suggested.")
    }

    // Dislike current meal and get a new one
    fun dislikeMeal(): Meal {
        return getKetoMeal()
    }

    // Check if the meal is keto-friendly based on the nutritional info
    private fun isKetoFriendly(meal: Meal): Boolean {
        return (meal.nutrition?.carbohydrates ?: 0.0) < 10 &&
                (meal.nutrition?.totalFat ?: 0.0) > (meal.nutrition?.protein ?: 0.0)
    }
}