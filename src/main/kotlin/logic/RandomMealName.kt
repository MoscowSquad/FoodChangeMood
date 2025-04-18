package org.example.logic
import org.example.model.NoFoodFoundException
import org.example.model.IncorrectMealNameException

class RandomMealNameProvider(
    private val mealRepository: MealRepository
) {

    fun getRandomMealName(): String {
        val meals = mealRepository.getAllMeals()
        if (meals.isNullOrEmpty()) {
            throw NoFoodFoundException("No meals available")
        }
        val randomMeal = meals.random()
        if (randomMeal.name.isBlank()) {
            throw IncorrectMealNameException("Meal name is empty")
        }

        return randomMeal.name
    }
}