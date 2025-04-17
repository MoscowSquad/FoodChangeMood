package org.example.data

import org.example.model.Meal
import org.example.model.NutritionRequest


class GymHelperController(private val useCase: GymHelperUseCase) {

    fun runGymHelper(caloriesInput: String, proteinInput: String) {
        try {
            val request = NutritionRequest(
                desiredCalories = caloriesInput.toDouble(),
                desiredProtein = proteinInput.toDouble()
            ).apply {
                require(desiredCalories >= 0 && desiredProtein >= 0) { "Calories and protein must be non-negative" }
            }

            val meals = useCase.findMatchingMeals(request).also { result ->
                if (result.isEmpty()) println("No meals found matching your criteria.")
                else println("Found ${result.size} matching meals:")
            }
            displayMeals(meals)
        } catch (e: NumberFormatException) {
            println("Error: Please enter valid numbers for calories and protein.")
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        }
    }

    private fun displayMeals(meals: List<Meal>) {
        meals.forEach { meal ->
            meal.nutrition?.let { nutrition ->
                println("${meal.name} (Calories: ${nutrition.calories}, Protein: ${nutrition.protein})")
            }
        }
    }
}
