package org.example.presentation

import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.utils.display

class ILovePotatoUI(
    private val getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase
) {
    operator fun invoke() {
        println("-- I Love Potato ---")
        val potatoMeals = getRandomMealsHavePotatoesUseCase.getRandomPotatoMeals()
        if (potatoMeals.isEmpty()) {
            println("No meals with potatoes found.")
        } else {
            println("Here are 10 random meals that include potatoes:")
            potatoMeals.display()
            println("Total meals with potatoes found: ${potatoMeals.size}")
        }
    }
}
