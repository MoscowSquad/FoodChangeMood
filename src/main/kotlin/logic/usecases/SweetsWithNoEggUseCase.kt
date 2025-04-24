package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class SweetsWithNoEggUseCase(private val mealRepository: MealRepository) {
    private val shownMeals = mutableSetOf<Int?>()

    fun getSweetsWithNoEggUseCase(): Meal {
        return mealRepository.getAllMeals()
            .filter(::isDataAcceptable)
            .randomOrNull()
            ?.also { meal -> shownMeals.add(meal.id) }
            ?: throw Exceptions.NoMealsFoundException("No sweets found without eggs.")
    }

    private fun isDataAcceptable(meal: Meal): Boolean {
        return meal.id != null &&
                meal.id !in shownMeals &&
                isSweetAndHasNoEgg(meal)
    }

    private fun isSweetAndHasNoEgg(meal: Meal): Boolean {
        val lowerTags = meal.tags?.map { it.lowercase() } ?: emptyList()
        val lowerDescription = meal.description?.lowercase().orEmpty()

        val isSweet = SWEET in lowerTags || SWEET in lowerDescription
        val containsEggInIngredients = meal.ingredients?.any { it.contains(EGG, ignoreCase = true) } == true
        val containsEggInDescription = lowerDescription.contains(EGG)

        return isSweet && !containsEggInIngredients && !containsEggInDescription
    }

    companion object {
        private const val SWEET = "sweet"
        private const val EGG = "egg"
    }
}