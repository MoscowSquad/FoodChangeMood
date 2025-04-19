package org.example.logic
import org.example.model.IncorrectMealNameException
import org.example.model.NoFoodFoundException

class RandomMealNameProvider(
    private val mealRepository: MealRepository
) {

    fun getRandomMealName(): String? {
        val meals = mealRepository.getAllMeals()
        if (meals.isEmpty()) {
            throw NoFoodFoundException("No meals available")
        }
        val randomMeal = meals.random()
        if (randomMeal.name?.isBlank() == true) {
            throw IncorrectMealNameException("Meal name is empty")
        }

        return randomMeal.name
    }
}