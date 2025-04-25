package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.logic.repository.SearchMatcher
import org.example.model.Exceptions
import org.example.model.Meal

class SearchMealByNameUseCase(
    private val matcher: SearchMatcher,
    private val repository: MealRepository
) {
    @Throws(
        Exceptions.EmptyKeywordException::class,
        Exceptions.KeywordNotFoundException::class
    )
    fun search(keyword: String): Meal {
        return repository.getAllMeals().search(keyword)
    }

    private fun List<Meal>.search(keyword: String): Meal {
        if (keyword.isEmpty())
            throw Exceptions.EmptyKeywordException()

        val matchedList = mutableListOf<Pair<Int, Meal>>()
        this.forEach { meal ->
            meal.name?.let { name ->
                val accuracy = matcher.getMatchAccuracy(name, keyword)
                if (accuracy != NOT_MATCHED)
                    matchedList.add(accuracy to meal)
            }
        }

        if (matchedList.isEmpty())
            throw Exceptions.KeywordNotFoundException(keyword = keyword)
        return matchedList.sortedBy { it.first }[0].second
    }

    companion object {
        const val MATCH_EXACTLY = 0
        const val MATCH_PARTIAL = 1
        const val NOT_MATCHED = -1
    }
}