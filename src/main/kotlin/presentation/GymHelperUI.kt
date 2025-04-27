package org.example.presentation

import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.NutritionRequest
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class GymHelperUI(
    private val gymMealsUseCase: GetGymMealsUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("--- Gym Helper ---")
        write("Enter calories: ")
        val caloriesInput = read()
        write("Enter protein: ")
        val proteinInput = read()

        try {
            val nutrition = NutritionRequest(caloriesInput.toDouble(), proteinInput.toDouble())
            val matchingMeals = gymMealsUseCase.invoke(nutrition)
            write("Meals matching your criteria (Calories: $caloriesInput, Protein: $proteinInput g):")
            matchingMeals.display()
            write("Total matching meals found: ${matchingMeals.size}")
        } catch (e: Exception) {
            write("Error: ${e.message}")
        }

    }
}
