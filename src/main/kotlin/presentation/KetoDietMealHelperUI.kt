package org.example.presentation

import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class KetoDietMealHelperUI(
    private val getKetoDietMealUseCase: GetKetoDietMealUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("Finding Keto-diet meal...")
        try {
            consoleIO.write("Your order is ready: ")
            getKetoDietMealUseCase.getKetoMeal()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            consoleIO.write(e.message)
        }
    }
}