package org.example.logic

import org.example.model.Meal

class GetRandomMealsHavePotatoesUseCase(
    private val mealRepository: MealRepository
) {
    fun getRandomPotatoMeals(): List<Meal> =
        mealRepository.getAllMeals().filter {
            it.ingredients?.contains("potatoes") ?: false
        }.shuffled().take(10)
}