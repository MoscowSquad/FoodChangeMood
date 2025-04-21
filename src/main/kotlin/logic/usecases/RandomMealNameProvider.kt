package org.example.logic.usecases

import org.example.logic.repository.MealRepository
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


// 1. Change the name of the class as it will end with UseCase
// 2. Use functional programming in conditions like this
//val meal = mealRepository.getAllMeals().takeIf { it.isNotEmpty() }
//    ?: throw Exceptions.IncorrectMealNameException()
//val randomMeal = meal.random().takeIf { it.name.isNotBlank() }
//    ?: throw Exceptions.NoFoodFoundException()