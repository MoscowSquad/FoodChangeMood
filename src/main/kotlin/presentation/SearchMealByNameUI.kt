package org.example.presentation

import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.example.utils.display

class SearchMealByNameUI(
    private val searchMealByNameUseCase: SearchMealByNameUseCase
) {
    operator fun invoke() {
        print("Enter meal name to search: ")
        readlnOrNull()?.let { mealName ->
            try {
                searchMealByNameUseCase.search(mealName).also { it.display() }
            } catch (e: Exceptions.KeywordNotFoundException) {
                println(e.message)
            } catch (e: Exceptions.EmptyKeywordException) {
                println("Please enter meal name")
            }
        } ?: println("Please enter valid meal name")
    }
}