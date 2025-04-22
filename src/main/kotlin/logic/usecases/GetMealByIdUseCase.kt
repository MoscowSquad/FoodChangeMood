package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetMealByIdUseCase(
    private val repository: MealRepository
) {
    fun getMealById(id: Int): Meal {
        return repository.getAllMeals().find { it.id == id }
            ?: throw Exceptions.NoMealsFound("No Meal Found with ID: $id")
    }
}

