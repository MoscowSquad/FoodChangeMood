package org.example.presentation

import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.utils.display

class SweetsWithNoEggUI(
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase
) {
    operator fun invoke() {
        println("🍬--- Sweets Without Eggs ---🍬")
        val sweet = sweetsWithNoEggUseCase.getSweetsWithNoEggUseCase()
        println("✨ Recommended Sweet:")
        sweet.display()
    }
}
