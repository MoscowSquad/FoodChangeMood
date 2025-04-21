package org.example.presentation

import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.utils.display

class SweetsWithNoEggUI(
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase
) {
    operator fun invoke() {
        println("--- Sweets with No Eggs ---")
        val sweet = sweetsWithNoEggUseCase.getSweetsWithNoEggUseCase()
        if (sweet == null) {
            println("No egg-free sweets available.")
        } else {
            println("Suggested Sweet:")
            sweet.display()
        }

    }
}
