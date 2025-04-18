package org.example.data

import org.example.model.Meal
import org.example.model.NutritionRequest

class InvalidInputException(message: String) : Exception(message)
class NoMealsFoundException : Exception("No meals found matching your criteria.")

class GymHelperController(private val useCase: GymHelperUseCase) {

    @Throws(InvalidInputException::class, NoMealsFoundException::class)
    fun runGymHelper(caloriesInput: String, proteinInput: String): List<Meal> {
        val request = try {
            NutritionRequest(
                desiredCalories = caloriesInput.toDouble(),
                desiredProtein = proteinInput.toDouble()
            ).apply {
                require(desiredCalories >= 0 && desiredProtein >= 0) {
                    throw InvalidInputException("Calories and protein must be non-negative")
                }
            }
        } catch (e: NumberFormatException) {
            throw InvalidInputException("Please enter valid numbers for calories and protein.")
        }

        val meals = useCase.findMatchingMeals(request)
        if (meals.isEmpty()) throw NoMealsFoundException()

        return meals
    }



    private fun displayMeals(meals: List<Meal>): List<String> {
        return meals.mapNotNull { meal ->
            meal.nutrition?.let { nutrition ->
                "${meal.name} (Calories: ${nutrition.calories}, Protein: ${nutrition.protein})"
            }
        }
    }}

