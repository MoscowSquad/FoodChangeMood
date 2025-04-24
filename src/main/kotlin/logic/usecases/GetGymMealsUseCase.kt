package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.model.NutritionRequest

import kotlin.math.abs

class GetGymMealsUseCase(private val mealRepository: MealRepository) {

    operator fun invoke(request: NutritionRequest): List<Meal> {
        val meals = mealRepository.getAllMeals()
            .filter { matchesRequest(it, request) }
        if (meals.isEmpty()) throw Exceptions.NoMealsFoundException()
        return meals
    }

    private fun matchesRequest(meal: Meal, request: NutritionRequest): Boolean {
        val nutrition = meal.nutrition ?: return false
        return isNutritionWithinTolerance(nutrition, request)
    }


    private fun isNutritionWithinTolerance(nutrition: Nutrition, request: NutritionRequest): Boolean {
        val calories = nutrition.calories ?: return false
        val protein = nutrition.protein ?: return false

        val calorieTolerance = request.desiredCalories * 0.1
        val proteinTolerance = request.desiredProtein * 0.1

        val isCalorieOk = abs(calories - request.desiredCalories) <= calorieTolerance
        val isProteinOk = abs(protein - request.desiredProtein) <= proteinTolerance

        return isCalorieOk && isProteinOk
    }
}
