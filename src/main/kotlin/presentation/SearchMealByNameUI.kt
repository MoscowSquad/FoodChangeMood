package org.example.presentation

import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class SearchMealByNameUI(
    private val searchMealByNameUseCase: SearchMealByNameUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Enter meal name to search: ")
        read().also { mealName ->
            try {
                searchMealByNameUseCase.search(mealName).also { it.display() }
            } catch (e: Exceptions.KeywordNotFoundException) {
                showError(e.message)
            } catch (e: Exceptions.EmptyKeywordException) {
                showError("Please enter meal name")
            }
        }
    }
}