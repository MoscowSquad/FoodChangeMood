package org.example.logic

import org.example.model.Meal

class GetHealthyFastFoodMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun getHealthyMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::isFastToPrepareMeal)
            .filter(::hasLowNutritionValues)
            .sortedBy { it.minutes }
    }

    private fun isFastToPrepareMeal(meal: Meal): Boolean {
        return meal.minutes <= MAX_PREP_TIME_MINUTES
    }

    private fun hasLowNutritionValues(meal: Meal): Boolean {
        val allMeals = mealRepository.getAllMeals()

        val totalFatThreshold = calculatePercentileThreshold(
            allMeals.map { it.nutrition.totalFat },
            LOW_NUTRITION_PERCENTILE
        )

        val saturatedFatThreshold = calculatePercentileThreshold(
            allMeals.map { it.nutrition.saturatedFat },
            LOW_NUTRITION_PERCENTILE
        )

        val carbsThreshold = calculatePercentileThreshold(
            allMeals.map { it.nutrition.carbohydrates },
            LOW_NUTRITION_PERCENTILE
        )

        return meal.nutrition.totalFat < totalFatThreshold &&
                meal.nutrition.saturatedFat < saturatedFatThreshold &&
                meal.nutrition.carbohydrates < carbsThreshold
    }

    private fun calculatePercentileThreshold(values: List<Double>, percentile: Double): Double {
        if (values.isEmpty()) return 0.0
        val sortedValues = values.sorted()
        val index = (sortedValues.size * percentile).toInt()
        return sortedValues[index]
    }

    companion object {
        const val MAX_PREP_TIME_MINUTES = 15
        const val LOW_NUTRITION_PERCENTILE = 0.25 // lowest 25%
    }
}