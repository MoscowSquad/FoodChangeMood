package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class FindItalianMealsForLargeGroupsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val meals = mealRepository.getAllMeals()
        //.filter(::isItalianMealAndGroupSuitableMeal)
        if (meals.isEmpty()) throw Exceptions.NoMealsFoundException()
        return meals

    }
}



