package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun getHealthyMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::isFastToPrepareMeal)
            .sorted()
            .takeIf { it.isNotEmpty()}?: throw Exceptions.NoMealsFoundException("No healthy meals found that can be prepared in $MAX_PREP_TIME_MINUTES minutes or less")


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
