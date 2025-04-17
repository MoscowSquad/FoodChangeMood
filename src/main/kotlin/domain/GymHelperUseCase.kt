package domain

import org.example.model.Meal
import org.example.model.Nutrition
import org.example.model.NutritionRequest
import data.MealRepository
import kotlin.math.abs

class GymHelperUseCase(private val repository: MealRepository) {

    fun findMatchingMeals(request: NutritionRequest): List<Meal> {
        return repository.getAllMeals()
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
        val calorieTolerance = request.desiredCalories * 0.1 // ±10%
        val proteinTolerance = request.desiredProtein * 0.1 // ±10%
        return abs(nutrition.calories - request.desiredCalories) <= calorieTolerance &&
                abs(nutrition.protein - request.desiredProtein) <= proteinTolerance
    }

    private fun calculateDistance(nutrition: Nutrition, request: NutritionRequest): Double {
        val calorieDiff = abs(nutrition.calories - request.desiredCalories)
        val proteinDiff = abs(nutrition.protein - request.desiredProtein)
        return calorieDiff + proteinDiff // Simple distance metric
    }
}