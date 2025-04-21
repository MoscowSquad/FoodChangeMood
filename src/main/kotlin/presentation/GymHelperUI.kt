package org.example.presentation

import org.example.data.GymHelperController
import org.example.utils.display
import java.util.*

class GymHelperUI(
    private val gymHelperController: GymHelperController,
) {
    private val scanner = Scanner(System.`in`)
    operator fun invoke() {
        println("--- Gym Helper ---")
        print("Enter calories: ")
        val caloriesInput = scanner.nextLine()
        print("Enter protein: ")
        val proteinInput = scanner.nextLine()

        try {
            val matchingMeals = gymHelperController.runGymHelper(caloriesInput, proteinInput)
            println("Meals matching your criteria (Calories: $caloriesInput, Protein: $proteinInput g):")
            matchingMeals.display()
            println("Total matching meals found: ${matchingMeals.size}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

    }
}
