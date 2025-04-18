package org.example.logic

import org.example.model.Meal

class GetMealByIdUseCase(
    private val repository: MealRepository
) {
    fun getMealById(id: Int): Meal? {
        return repository.getAllMeals().find { it.id == id }
    }
}