package logic.usecases

import org.example.model.Meal

 fun createMeal(name: String, minutes: Int?): Meal {
    return Meal(
        id = 0,
        name = name,
        contributorId = 0,
        submitted = false.toString(),
        tags = emptyList(),
        nutrition = null,
        nSteps = 0,
        steps = emptyList(),
        description = "",
        ingredients = emptyList(),
        nIngredients = 0,
        minutes = minutes
    )
}