package org.example.presentation

import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class GetSeaFoodMealsUI(
    private val getSeafoodByProteinContentUseCase: GetSeafoodByProteinContentUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Finding all seafood meals sorted by protein content...")
        try {
            write("Your order is ready: ")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            write(e.message)
        }
    }
}
