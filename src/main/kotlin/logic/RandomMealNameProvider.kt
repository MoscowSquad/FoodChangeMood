package org.example.logic

import org.example.model.Exceptions
import org.example.model.Meal

class RandomMealNameProvider(
    private val mealRepository: MealRepository
) {
    fun getRandomMeal(): Meal {
        val meals = mealRepository.getAllMeals()
        if (meals.isEmpty()) {
            throw Exceptions.NoFoodFoundException("No meals available")
        }
        val randomMeal = meals.random()
        if (randomMeal.name?.isBlank() == true) {
            throw Exceptions.IncorrectMealNameException("Meal name is empty")
        }

        return randomMeal
    }
}