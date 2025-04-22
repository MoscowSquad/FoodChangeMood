package org.example.presentation

import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.utils.display

class ILovePotatoUI(
    private val getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase
) {
    operator fun invoke() {
        println("🥔 -- I LOVE POTATO -- 🥔")

        runCatching { getRandomMealsHavePotatoesUseCase() }
            .onSuccess { potatoMeals ->
                println("Here are some tasty meals with potatoes:")
                potatoMeals.display()
                println("Total shown: ${potatoMeals.size}")
            }
            .onFailure {
                println("❌ No meals with potatoes found.")
            }
    }
}
