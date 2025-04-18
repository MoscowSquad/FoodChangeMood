package org.example.logic

import org.example.model.Meal

class GetSeafoodByProteinContent(private val repository: MealRepository) {
    fun getSeafoodMealsByProteinContent(): List<Meal> {
        return repository.getAllMeals()
            .filter(::bySeafood)
            .sortedByDescending { it.nutrition?.protein }
    }

    private fun bySeafood(meal: Meal): Boolean {
        return meal.tags?.contains("seafood") ?: false
                || meal.description?.contains("seafood") ?: false
    }
}

