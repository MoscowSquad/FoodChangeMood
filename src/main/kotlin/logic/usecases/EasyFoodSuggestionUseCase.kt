package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {
    fun suggestTenRandomMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter { currentMeal ->
                currentMeal.minutes != null
                        && currentMeal.nIngredients != null
                        && currentMeal.nSteps != null
                        && currentMeal.name != null &&
                        currentMeal.minutes <= 30 && currentMeal.nIngredients <= 5 && currentMeal.nSteps <= 6
            }
            .shuffled()
            .take(10)
    }
}

// 1. Don't use shuffled use random indices instead
// 2. Extract Constant value into companion object
// 3. Extract the filter lambda outside into another function
// 4. Check if the list is empty or not if it is throw a custom exception and write it on the Exceptions file