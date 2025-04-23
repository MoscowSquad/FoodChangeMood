package org.example.presentation

import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.example.utils.display

class GetSeaFoodMealsUI(
    private val getSeafoodByProteinContentUseCase: GetSeafoodByProteinContentUseCase
) {
    operator fun invoke() {
        println("Finding all seafood meals sorted by protein content...")
        try {
            println("Your order is ready: ")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            println(e.message)
        }
    }
}
