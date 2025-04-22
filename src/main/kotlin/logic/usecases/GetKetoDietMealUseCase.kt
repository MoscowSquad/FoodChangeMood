package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetKetoDietMealUseCase(private val repository: MealRepository) {
    private val suggestedMeals = mutableSetOf<Meal>()

    // Suggest a keto meal
    fun getKetoMeal(): Meal {
        repository.getAllMeals()
            .shuffled()
            .forEach { meal ->
                if (meal.isKetoFriendly() && meal.notSuggestedBefore().not()) {
                    suggestedMeals.add(meal)
                    return meal
                }
            }

        throw Exceptions.NoMealsFoundException("There no a Keto meals")
    }

    private fun Meal.notSuggestedBefore(): Boolean {
        return suggestedMeals.contains(this)
    }

    // Check if the meal is keto-friendly based on the nutritional info
    private fun Meal.isKetoFriendly(): Boolean {
        return (nutrition?.carbohydrates ?: 0.0) < 10 && ((nutrition?.totalFat ?: 0.0) > (nutrition?.protein ?: 0.0))
    }
}


// for getKetoMeal function use filter{} first with the `meal.isKetoFriendly() && meal.notSuggestedBefore().not()` condition
// then implement the logic for selecting a random index from the returned list from filter function
// inside the logic for selecting a random index add the new selected item to the suggestedMeals list.
// Remove the shuffled() function and use random indices for better performance