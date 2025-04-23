package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.utils.takeRandomMeals

class GetRandomMealsHavePotatoesUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val potatoMeals = mealRepository.getAllMeals()
            .filter(::filterPotatoMeals)
            .takeIf { it.isNotEmpty() }
            ?: throw Exceptions.NoMealsFound("No meals found with potatoes.")

        return potatoMeals.takeRandomMeals(MAX_MEALS)
    }

    private fun filterPotatoMeals(meal: Meal) =
        meal.ingredients?.any { ingredient -> ingredient.contains("potato", ignoreCase = true) } == true

    companion object {
        private const val MAX_MEALS = 10
    }
}