package org.example.presentation

import org.example.logic.usecases.GetHighCaloriesMealsUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class HighCaloriesMealsUI(
    private val getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding high calories meals...")
        try {
            println("Your order is ready: ")
            getHighCaloriesMealsUseCase.nextMeal().also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            consoleIO.write(e.message)
        }
    }
}
