package org.example.presentation

import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class SweetsWithNoEggUI(
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("🍬--- Sweets Without Eggs ---🍬")
        runCatching { sweetsWithNoEggUseCase() }
            .onSuccess {
                write("✨ Recommended Sweet:")
                it.display()
            }
            .onFailure {
                write(it.message)
            }
    }
}
