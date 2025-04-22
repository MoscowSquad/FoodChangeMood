package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun getHealthyMeals(): List<Meal> {
        val allMeals = try {
            mealRepository.getAllMeals()
        } catch (e: Exception) {
            throw Exceptions.NoFoodFoundException("Failed to retrieve meals from repository: ${e.message}")
        }

        if (allMeals.isEmpty()) {
            throw Exceptions.NoMealsFoundException()
        }

        val healthyFastMeals = allMeals.filter(::isFastToPrepareMeal)

        if (healthyFastMeals.isEmpty()) {
            throw Exceptions.NoMealsFound("No healthy meals found that can be prepared in $MAX_PREP_TIME_MINUTES minutes or less")
        }

        return healthyFastMeals.sorted()
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
class NoMealsFound(message: String = "No meals found matching the specified criteria.") : Exceptions(message)
class MealNotFoundException(message: String = "The requested meal could not be found in the repository.") : Exceptions(message)