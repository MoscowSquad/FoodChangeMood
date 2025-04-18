package org.example.logic

import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun getHealthyMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::isFastToPrepareMeal)
            .sorted()
    }

    private fun isFastToPrepareMeal(meal: Meal): Boolean {
        if (meal.minutes == null)
            return false

        return meal.minutes <= MAX_PREP_TIME_MINUTES
    }

    companion object {
        const val MAX_PREP_TIME_MINUTES = 15
        const val LOW_NUTRITION_PERCENTILE = 0.25 // lowest 25%
    }
}