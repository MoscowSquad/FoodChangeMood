package logic.usecases

import org.example.model.Meal

fun createMeal(
    name: String,
    nutrition: Double,
    Calories: Double,
    Total_Fat: Double,
    Sodium: Double,
    Protein: Double,
    Saturated_Fat: Double,
    Carbohydrates: Double
): Meal {
    return Meal(
        id = 0,
        name = name,
        contributorId = 0,
        submitted = false.toString(),
        tags = emptyList(),
        nutrition = listOf(),
        nSteps = 0,
        steps = emptyList(),
        description = "",
        ingredients = emptyList(),
        nIngredients = 0,
        minutes = null
    )
}