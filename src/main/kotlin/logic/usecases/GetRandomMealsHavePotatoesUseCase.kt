package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import kotlin.random.Random

class GetRandomMealsHavePotatoesUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val potatoMeals = mealRepository.getAllMeals()
            .filter(::filterPotatoMeals)
            .takeIf { it.isNotEmpty() }
            ?: throw Exceptions.NoMealsFound("No meals found with potatoes.")

        return generateSequence { Random.nextInt(potatoMeals.size) }
            .distinct()
            .take(MAX_MEALS.coerceAtMost(potatoMeals.size)).also { println(it) }
            .toList()
            .map(potatoMeals::get)
    }

    private fun filterPotatoMeals(meal: Meal) =
        meal.ingredients?.any { ingredient -> ingredient.contains("potato", ignoreCase = true) } == true

    companion object {
        private const val MAX_MEALS = 10
    }
}