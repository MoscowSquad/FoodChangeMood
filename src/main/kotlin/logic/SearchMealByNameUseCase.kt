package org.example.logic

import org.example.model.BlankKeywordException
import org.example.model.KeywordNotFoundException
import org.example.model.Meal

const val MATCH_EXACTLY = 0
const val MATCH_PARTIAL = 1
const val NOT_MATCHED = -1

class SearchMealByNameUseCase(
    private val matcher: SearchMatcher,
    private val repository: MealRepository
) {
    fun search(keyword: String): Meal {
        if (keyword.isBlank())
            throw BlankKeywordException()

        val matchedList = mutableListOf<Pair<Int, Meal>>()
        repository.getAllMeals().forEach { meal ->
            val accuracy = matcher.getMatchAccuracy(meal.name, keyword)
            if (accuracy == MATCH_EXACTLY)
                return meal
            else if (accuracy != NOT_MATCHED)
                matchedList.add(accuracy to meal)
        }

        if (matchedList.isEmpty())
            throw KeywordNotFoundException(keyword = keyword)

        return matchedList.sortedBy { it.first }[0].second
    }
}