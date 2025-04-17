package org.example.logic

import org.example.model.Meal

class GetSeafoodByProteinContent(private val mealRepository: MealRepository) {
    fun getSeafoodMealsByProteinContent(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::bySeafood)
            .sortedByDescending { it.nutrition.protein }
    }

    private fun bySeafood(meal: Meal): Boolean {
        return meal.tags.contains("seafood")
    }
}

