package org.example.presentation

import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.utils.display

class KetoDietMealHelperUI(
    private val getKetoDietMealUseCase: GetKetoDietMealUseCase
) {
    operator fun invoke() {
        println("Finding Keto-diet meal...")
        try {
            println("Your order is ready: ")
            getKetoDietMealUseCase.getKetoMeal()
                .also { it.display() }
        } catch (e: Exceptions.NoMealsFoundException) {
            println(e.message)
        }
    }
}
