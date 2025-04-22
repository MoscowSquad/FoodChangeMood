package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import kotlin.random.Random

class GetKetoDietMealUseCase(private val repository: MealRepository) {

    private val suggestedMeals = mutableSetOf<Meal>()
    private var currentMeal: Meal? = null

    // Get a new keto-friendly meal
    fun getKetoMeal(): Meal {
        val available = repository.getAllMeals().filter { it.isKetoFriendly() && !suggestedMeals.contains(it) }

        if (available.isEmpty()) {
            throw Exceptions.MealNotFoundException("No more keto-friendly meals available.")
        }

        val randomMeal = available[Random.nextInt(available.size)]
        currentMeal = randomMeal
        suggestedMeals.add(randomMeal)
        return randomMeal
    }

    // Mark the current meal as liked (e.g., show full details)
    fun likeMeal(): Meal {
        return currentMeal ?: throw Exceptions.MealNotFoundException("No meal is currently suggested.")
    }

    // Dislike current meal and get a new one
    fun dislikeMeal(): Meal {
        return getKetoMeal()
    }

    // Internal: is this meal keto?
    private fun Meal.isKetoFriendly(): Boolean {
        return (nutrition?.carbohydrates ?: 0.0) < 10 &&
                (nutrition?.totalFat ?: 0.0) > (nutrition?.protein ?: 0.0)
    }
}
