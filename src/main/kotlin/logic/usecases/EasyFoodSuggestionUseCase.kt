package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {
    fun suggestTenRandomMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::filterEasyFood)
            .takeIf { it.isNotEmpty() }
            ?.takeRandomMeals(NUMBER_OF_MEALS)
            ?: throw MealsNotFoundException()
    }

    private fun filterEasyFood(currentMeal: Meal): Boolean {
        return currentMeal.minutes != null
                && currentMeal.nIngredients != null
                && currentMeal.nSteps != null
                && currentMeal.name != null &&
                currentMeal.minutes <= MAXIMUM_PREPARATION_MINUTES
                && currentMeal.nIngredients <= MAXIMUM_INGREDIENT_NUMBER
                && currentMeal.nSteps <= MAXIMUM_STEPS_NUMBER
    }

    private fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
        return generateSequence { this.random() }
            .distinct()
            .take(numberOfMeals)
            .toList()
    }

    companion object {
        private const val NUMBER_OF_MEALS = 10
        private const val MAXIMUM_PREPARATION_MINUTES = 30
        private const val MAXIMUM_INGREDIENT_NUMBER = 5
        private const val MAXIMUM_STEPS_NUMBER = 5
    }
}

class MealsNotFoundException :
    Exception("\"The Easy meals are missing from the list. Please check if there were added correctly.\"\n")
