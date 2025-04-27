package org.example.presentation

import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class ILovePotatoUI(
    private val getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("🥔 -- I LOVE POTATO -- 🥔")

        runCatching { getRandomMealsHavePotatoesUseCase() }
            .onSuccess { potatoMeals ->
                write("Here are some tasty meals with potatoes:")
                potatoMeals.display()
                write("Total shown: ${potatoMeals.size}")
            }
            .onFailure {
                write(it.message)
            }
    }
}
