package org.example.usecases

import org.example.model.Meal

class SweetsWithNoEggUseCase(private val meals: List<Meal>) {
    operator fun invoke(): Meal? {
        return meals
            .filter { it.tags.contains("desserts") && !it.ingredients.contains("egg") }
            .randomOrNull()
    }
}