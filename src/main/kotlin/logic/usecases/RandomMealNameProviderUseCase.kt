package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class RandomMealNameProviderUseCase(
    private val mealRepository: MealRepository
) {
    private fun isValidMeal(meal: Meal): Boolean = !meal.name.isNullOrBlank()
    private var meal: Meal?= null
    fun getRandomMeal(): Meal {
        meal = mealRepository.getAllMeals()
            .filter(::isValidMeal)
            .randomOrNull()
            ?: throw Exceptions.NoMealsFoundException()
        return  meal!!
    }

    fun isSuggestRight(suggestion: Int?): Boolean {
        if (meal == null || suggestion==null){
            throw Exceptions.NoMealsFoundException()
        }
        return meal?.minutes == suggestion
    }
}
