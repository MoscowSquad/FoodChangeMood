package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class FindItalianMealsForLargeGroupsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val meals = mealRepository.getAllMeals()
            .filter(::isItalianMealAndGroupSuitableMeal)
        if (meals.isEmpty()) throw Exceptions.NoMealsFoundException()
        return meals

    }
    private fun isItalianMealAndGroupSuitableMeal(meal: Meal): Boolean {

        val tags = meal.tags?.map { it.lowercase() }.orEmpty()
        val description = meal.description?.lowercase().orEmpty()

        val isItalian = "italian" in tags || "italian" in description
        val isForGroups = listOf("for-large-groups", "dinner-party", "potluck").any { it in tags }

        return isItalian && isForGroups
    }
}
