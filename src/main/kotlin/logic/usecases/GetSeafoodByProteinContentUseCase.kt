package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions.NoMealsFoundException
import org.example.model.Meal

class GetSeafoodByProteinContentUseCase(private val repository: MealRepository) {
    fun getSeafoodMealsByProteinContent(): List<Meal> {
        return repository.getAllMeals()
            .filter(::bySeafood)
            .filter { it.nutrition?.protein != null }
            .ifEmpty { throw NoMealsFoundException() }
            .sortedByDescending { it.nutrition?.protein }
    }

    private fun bySeafood(meal: Meal): Boolean {
        return meal.tags?.contains("seafood") ?: false
                || meal.description?.contains("seafood") ?: false
    }
}