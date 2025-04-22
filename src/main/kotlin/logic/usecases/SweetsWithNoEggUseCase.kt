package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal

class SweetsWithNoEggUseCase(private val mealRepository: MealRepository) {
    private val shownMeals = mutableSetOf<Int>()
    fun getSweetsWithNoEggUseCase(): Meal {
        return mealRepository.getAllMeals()
            .filter(::isSweetAndHasNoEgg)
            .filter { it.id != null && it.id !in shownMeals }
            .randomOrNull()?.also { meal -> meal.id?.let(shownMeals::add) }
            ?: throw Exceptions.NoMealsFound("No sweets found without eggs.")
    }

    private fun isSweetAndHasNoEgg(meal: Meal): Boolean {
        return (meal.tags?.contains(SWEET) == true || meal.description?.contains(SWEET) == true) &&
                (meal.tags?.contains(EGG) == false || meal.description?.contains(EGG) == false)
    }

    companion object {
        private const val SWEET = "sweet"
        private const val EGG = "egg"
    }
}