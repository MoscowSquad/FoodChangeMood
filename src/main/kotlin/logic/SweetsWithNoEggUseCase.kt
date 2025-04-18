package org.example.logic

import org.example.model.Meal

class SweetsWithNoEggUseCase(private val mealRepository: MealRepository) {
    fun getSweetsWithNoEggUseCase(): Meal? {
        return mealRepository.getAllMeals()
            .filter { it.tags?.contains("desserts") ?: false && !(it.ingredients?.contains("eggs") ?: false) }
            .randomOrNull()
    }
}