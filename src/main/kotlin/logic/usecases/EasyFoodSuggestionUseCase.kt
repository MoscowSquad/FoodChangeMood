package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.utils.takeRandomMeals

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {
    fun suggestTenRandomMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::filterEasyFood)
            .takeIf { it.isNotEmpty() }
            ?.let { it.takeRandomMeals(NUMBER_OF_MEALS.coerceAtMost(it.size)) }
            ?: throw Exceptions.NoMealsFoundException()
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


    companion object {
        private const val NUMBER_OF_MEALS = 10
        private const val MAXIMUM_PREPARATION_MINUTES = 30
        private const val MAXIMUM_INGREDIENT_NUMBER = 5
        private const val MAXIMUM_STEPS_NUMBER = 5
    }
}


