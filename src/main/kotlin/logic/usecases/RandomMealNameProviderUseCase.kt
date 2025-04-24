package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class RandomMealNameProviderUseCase(
    private val mealRepository: MealRepository
) {
    private fun isValidMeal(meal: Meal): Boolean = !meal.name.isNullOrBlank()

    fun getRandomMeal(): Meal = mealRepository.getAllMeals()
        .filter(::isValidMeal)
        .randomOrNull()
        ?: throw Exceptions.NoMealsFoundException()
}