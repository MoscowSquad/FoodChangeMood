package org.example.presentation

import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class SweetsWithNoEggUI(
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("🍬--- Sweets Without Eggs ---🍬")
        val sweet = sweetsWithNoEggUseCase.getSweetsWithNoEggUseCase()
        consoleIO.write("✨ Recommended Sweet:")
        sweet.display()
    }
}
