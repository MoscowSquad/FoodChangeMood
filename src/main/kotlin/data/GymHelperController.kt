package org.example.data


import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.NutritionRequest


class GymHelperController(private val useCase: GetGymMealsUseCase) {

    @Throws(Exceptions::class)
    fun runGymHelper(caloriesInput: String, proteinInput: String): List<Meal> {
        val request = try {
            NutritionRequest(
                desiredCalories = caloriesInput.toDouble(),
                desiredProtein = proteinInput.toDouble()
            ).apply {
                require(desiredCalories >= 0 && desiredProtein >= 0) {
                    throw Exceptions.InvalidInputException("Calories and protein must be non-negative")
                }
            }
        } catch (e: NumberFormatException) {
            throw Exceptions.InvalidInputException("Please enter valid numbers for calories and protein.")
        }

        return useCase(request)
    }
}
