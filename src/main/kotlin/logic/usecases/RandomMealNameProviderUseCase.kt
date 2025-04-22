package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
class RandomMealNameProviderUseCase(
    private val mealRepository: MealRepository
) {
    fun getRandomMeal(): Meal {
        val meals = mealRepository.getAllMeals()

        if (meals.isEmpty()) {
            throw Exceptions.IncorrectMealNameException()
        }

        return meals
            .filter { !it.name.isNullOrBlank() }
            .randomOrNull()
            ?: throw Exceptions.NoFoodFoundException()
    }
}