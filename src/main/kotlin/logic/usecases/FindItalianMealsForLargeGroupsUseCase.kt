package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal

class FindItalianMealsForLargeGroupsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val allMeals = getAllMeals()
        val filteredMeals = filterItalianAndGroupSuitableMeals(allMeals)
        return filteredMeals
    }

    private fun getAllMeals(): List<Meal> {
        return mealRepository.getAllMeals()
    }

    private fun filterItalianAndGroupSuitableMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { meal ->
            isItalianMeal(meal) && isGroupSuitableMeal(meal)
        }
    }

    private fun isItalianMeal(meal: Meal): Boolean {
        val tagsLower = meal.tags?.map { it.lowercase() }
        if (tagsLower != null) {
            return tagsLower.contains("italian") || meal.description?.lowercase()?.contains("italian") ?: false
        }
        return false
    }

    private fun isGroupSuitableMeal(meal: Meal): Boolean {
        val tagsLower = meal.tags?.map { it.lowercase() }
        if (tagsLower != null) {
            return tagsLower.contains("for-large-groups") ||
                    tagsLower.contains("dinner-party") ||
                    tagsLower.contains("potluck")
        }
        return false
    }
}


// 1. Add function to get high quality data
// 2. Merge isItalianMeal and isGroupSuitableMeal into one function and use filter(::theNameOfTheNewFunction)
// 3. Reduce the logic into the invoke function by reducing the number of called functions
// 4. Check if the list is empty or not if it is throw a custom exception and write it on the Exceptions file