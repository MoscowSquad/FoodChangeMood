package org.example.presentation

import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class KetoDietMealHelperUI(
    private val getKetoDietMealUseCase: GetKetoDietMealUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("Finding Keto-diet meal...")
        try {
            write("Your order is ready: ")
            getKetoDietMealUseCase.getKetoMeal()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            write(e.message)
        }
    }
}