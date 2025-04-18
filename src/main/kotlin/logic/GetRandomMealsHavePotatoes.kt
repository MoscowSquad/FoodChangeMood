package org.example.logic

import org.example.utils.Meal

class GetRandomMealsHavePotatoes(
    private val meals: List<Meal>
) {
    fun getRandomPotatoMeals(): List<Meal> = meals
        .filter {
            it.ingredients?.contains("potatoes") ?: false
        }.shuffled().take(10)
}