package org.example.logic.usecases
import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
class RandomMealNameProviderUseCase(
    private val mealRepository: MealRepository
) {
    private var meal: Meal? = null
    fun getRandomMeal(): Meal {
        val validMeals = mealRepository.getAllMeals()
            .filter { it.name?.isNotBlank() == true }
        if (validMeals.isEmpty()) {
            throw Exceptions.NoMealsFoundException()
        }
        meal = validMeals.random()
        return meal as Meal
    }
    fun isSuggestRight(suggestion: Int?): Boolean {
        val currentMeal = meal ?: throw Exceptions.NoMealsFoundException()
        if (suggestion == null) {
            throw Exceptions.NoMealsFoundException()
        }
        return currentMeal.minutes == suggestion
    }
}