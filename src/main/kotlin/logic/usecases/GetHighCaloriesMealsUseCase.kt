package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class GetHighCaloriesMealsUseCase(private val repository: MealRepository) {
    private val suggestedList = mutableSetOf<Meal>()

    @Throws(
        Exceptions.NoMealsFoundException::class,
    )
    operator fun invoke(): Meal {
        return repository.getAllMeals()
            .filter(::byHighCalories)
            .getNextHighCaloriesMeal()
    }

    private fun List<Meal>.getNextHighCaloriesMeal(): Meal {
        forEach { meal ->
            if (!suggestedList.contains(meal)) {
                suggestedList.add(meal)
                return meal
            }
        }
        throw Exceptions.NoMealsFoundException()
    }

    private fun byHighCalories(meal: Meal): Boolean {
        if (meal.nutrition?.calories == null)
            return false

        return meal.nutrition.calories > HIGH_CALORIE_THRESHOLD
    }

    companion object {
        const val HIGH_CALORIE_THRESHOLD = 700
    }
}