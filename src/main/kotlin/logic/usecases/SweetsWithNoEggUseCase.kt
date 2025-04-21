package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class SweetsWithNoEggUseCase(private val mealRepository: MealRepository) {
    fun getSweetsWithNoEggUseCase(): Meal? {
        return mealRepository.getAllMeals()
            .filter { it.tags?.contains("desserts") ?: false && !(it.ingredients?.contains("eggs") ?: false) }
            .randomOrNull()
    }
}