package org.example.usecases

import org.example.logic.MealRepository
import org.example.model.Meal

class SweetsWithNoEggUseCase(private val mealRepository: MealRepository) {
    operator fun invoke(): Meal? {
        return mealRepository.getAllMeals()
            .filter { it.tags.contains("desserts") && !it.ingredients.contains("eggs") }
            .randomOrNull()
    }
}