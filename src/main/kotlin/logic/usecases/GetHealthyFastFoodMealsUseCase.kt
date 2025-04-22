package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun getHealthyMeals(): List<Meal> {
        val allMeals = try {
            mealRepository.getAllMeals().also { meals ->
                if (meals.isEmpty()) {
                    throw Exceptions.NoMealsFoundException()
                }
            }
        } catch (e: Exception) {
            throw Exceptions.NoFoodFoundException("Failed to retrieve meals from repository: ${e.message}")
        }

        return allMeals
            .filter(::isFastToPrepareMeal)
            .also { healthyFastMeals ->
                if (healthyFastMeals.isEmpty()) {
                    throw Exceptions.NoMealsFound("No healthy meals found that can be prepared in $MAX_PREP_TIME_MINUTES minutes or less")
                }
            }
            .sorted()
    }

    private fun isFastToPrepareMeal(meal: Meal): Boolean {
        if (meal.minutes == null) {
            return false
        }

        return meal.minutes <= MAX_PREP_TIME_MINUTES
    }

    companion object {
        const val MAX_PREP_TIME_MINUTES = 15
    }
}
