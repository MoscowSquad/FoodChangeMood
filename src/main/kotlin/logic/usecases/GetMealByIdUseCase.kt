package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class GetMealByIdUseCase(
    private val repository: MealRepository
) {
    fun getMealById(id: Int): Meal? {
        return repository.getAllMeals().find { it.id == id }
    }
}


// 3. Use the exception you have implemented if the list is empty
// 4. Fix the function as you want it to function