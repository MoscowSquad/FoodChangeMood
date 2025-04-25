package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetKetoDietMealUseCase(private val repository: MealRepository) {
    private val suggestedMeals = mutableSetOf<Meal>()
    private var currentMeal: Meal? = null
    private val dislikedMeals = mutableSetOf<Meal>()
    private var availableMeals: List<Meal> = emptyList()

    init {
        reloadMeals()
    }

    // Suggest a keto meal
    fun getKetoMeal(): Meal {
        val available = repository.getAllMeals()
            .filter { isKetoFriendly(it) }
            .filterNot { suggestedMeals.contains(it) }
            .filterNot { dislikedMeals.contains(it) }

        return available.randomOrNull()
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

    fun dislikeMeal(): Meal {
        val meal = currentMeal ?: throw Exceptions.NoMealsFoundException("No meal to dislike.")

        dislikedMeals.add(meal)
        currentMeal = null
        reloadMeals()

        return try {
            getKetoMeal()
        } catch (e: Exceptions.NoMealsFoundException) {
            throw Exceptions.NoMealsFoundException("No more keto-friendly meals available after disliking current meal.")
        }
    }

    private fun reloadMeals() {
        availableMeals = repository.getAllMeals()
            .filter { isKetoFriendly(it) }
            .filterNot { dislikedMeals.contains(it) }
    }

    // Check if the meal is keto-friendly based on the nutritional info
    fun isKetoFriendly(meal: Meal): Boolean {
        val nutrition = meal.nutrition ?: return false
        return nutrition.carbohydrates!! < 10.0 && nutrition.totalFat!! > nutrition.protein!!
    }
}