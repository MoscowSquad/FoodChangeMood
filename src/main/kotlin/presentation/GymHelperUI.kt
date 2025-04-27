package org.example.presentation

import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.NutritionRequest
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class GymHelperUI(
    private val gymMealsUseCase: GetGymMealsUseCase,
    private val consoleIO: ConsoleIO
) {

    operator fun invoke() {
        consoleIO.write("--- Gym Helper ---")
        consoleIO.write("Enter calories: ")
        val caloriesInput = consoleIO.read()
        consoleIO.write("Enter protein: ")
        val proteinInput = consoleIO.read()

        try {
            val nutrition = NutritionRequest(caloriesInput.toDouble(), proteinInput.toDouble())
            val matchingMeals = gymMealsUseCase.invoke(nutrition)
            consoleIO.write("Meals matching your criteria (Calories: $caloriesInput, Protein: $proteinInput g):")
            matchingMeals.display()
            consoleIO.write("Total matching meals found: ${matchingMeals.size}")
        } catch (e: Exception) {
            consoleIO.write("Error: ${e.message}")
        }

    }
}
