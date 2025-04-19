package org.example.logic

import org.example.model.Meal
import org.example.model.Nutrition
import org.example.model.NutritionRequest

import kotlin.math.abs

class GetGymMealsUseCase(private val mealRepository: MealRepository) {

    fun findMatchingMeals(request: NutritionRequest): List<Meal> {
        return mealRepository.getAllMeals()
            .filter { it.nutrition != null }
            .filter { meal ->
                meal.nutrition?.let { nutrition ->
                    isNutritionMatch(nutrition, request)
                } ?: false
            }
            .sortedBy { meal ->
                meal.nutrition?.let { calculateDistance(it, request) } ?: Double.MAX_VALUE
            }

    }

    private fun isNutritionMatch(nutrition: Nutrition, request: NutritionRequest): Boolean {
        val calories = nutrition.calories ?: return false
        val protein = nutrition.protein ?: return false

        val calorieTolerance = request.desiredCalories * 0.1
        val proteinTolerance = request.desiredProtein * 0.1

        return abs(calories - request.desiredCalories) <= calorieTolerance &&
                abs(protein - request.desiredProtein) <= proteinTolerance
    }

    private fun calculateDistance(nutrition: Nutrition, request: NutritionRequest): Double {
        val calories = nutrition.calories ?: return Double.MAX_VALUE
        val protein = nutrition.protein ?: return Double.MAX_VALUE

        val calorieDiff = abs(calories - request.desiredCalories)
        val proteinDiff = abs(protein - request.desiredProtein)

        return calorieDiff + proteinDiff
    }

}