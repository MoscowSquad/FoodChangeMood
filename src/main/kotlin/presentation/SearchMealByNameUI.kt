package org.example.presentation

import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class SearchMealByNameUI(
    private val searchMealByNameUseCase: SearchMealByNameUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Enter meal name to search: ")
        consoleIO.read().also { mealName ->
            try {
                searchMealByNameUseCase.search(mealName).also { it.display() }
            } catch (e: Exceptions.KeywordNotFoundException) {
                consoleIO.write(e.message)
            } catch (e: Exceptions.EmptyKeywordException) {
                consoleIO.write("Please enter meal name")
            }
        }
    }
}