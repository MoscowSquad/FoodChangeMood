package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Exceptions
import org.example.model.Meal
import kotlin.random.Random

class SearchMealByCountryUseCase(
    private val repository: MealRepository
) {
    fun searchMealsByCountry(country: String): List<Meal> {
        val mutableListMeal = repository.getAllMeals()
            .filter { meal -> meal.matchesCountry(country) }
            .takeIf { it.isNotEmpty() }
            ?.take(MAX_MEALS)
            ?.toMutableList()
            ?: throw Exceptions.NoMealsFound("No Meal Found With Country: $country")

        mutableListMeal.fisherYatesShuffle()
        return mutableListMeal

    }



    private fun Meal.matchesCountry(country: String): Boolean{
        val lowerCountry = country.lowercase()
        return tags?.any{ it.contains(lowerCountry , ignoreCase = true) } == true ||
                name?.contains(lowerCountry , ignoreCase = true) == true ||
                description?.contains(lowerCountry , ignoreCase = true) == true
    }

    private fun <T> MutableList<T>.fisherYatesShuffle() {
        for (i in lastIndex downTo 1) {
            val j = Random.nextInt(i + 1)
            val temp = this[i]
            this[i] = this[j]
            this[j] = temp
        }
    }
    companion object {
        const val MAX_MEALS = 20
    }
}

