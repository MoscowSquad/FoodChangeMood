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
        runCatching { sweetsWithNoEggUseCase() }
            .onSuccess {
                consoleIO.write("✨ Recommended Sweet:")
                it.display()
            }
            .onFailure {
                consoleIO.write(it.message)
            }
    }
}
