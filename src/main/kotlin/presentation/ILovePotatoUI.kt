package org.example.presentation

import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class ILovePotatoUI(
    private val getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("🥔 -- I LOVE POTATO -- 🥔")

        runCatching { getRandomMealsHavePotatoesUseCase() }
            .onSuccess { potatoMeals ->
                consoleIO.write("Here are some tasty meals with potatoes:")
                potatoMeals.display()
                consoleIO.write("Total shown: ${potatoMeals.size}")
            }
            .onFailure {
                consoleIO.write("❌ No meals with potatoes found.")
            }
    }
}
