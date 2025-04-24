package org.example.presentation

import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class GetSeaFoodMealsUI(
    private val getSeafoodByProteinContentUseCase: GetSeafoodByProteinContentUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding all seafood meals sorted by protein content...")
        try {
            consoleIO.write("Your order is ready: ")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            consoleIO.write(e.message)
        }
    }
}
