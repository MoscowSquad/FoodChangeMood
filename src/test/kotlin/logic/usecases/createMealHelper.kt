package logic.usecases

import org.example.model.Meal
import org.example.model.Nutrition


fun createMeal(
    name: String? = null,
    id: Int? = null,
    minutes: Int? = null,
    contributorId: Int? = null,
    submitted: String? = null,
    tags: List<String>? = null,
    nutrition: Nutrition? = null,
    nSteps: Int? = null,
    steps: List<String>? = null,
    description: String? = null,
    ingredients: List<String>? = null,
    nIngredients: Int? = null
) = Meal(
    name = name,
    id = id,
    minutes = minutes,
    contributorId = contributorId,
    submitted = submitted,
    tags = tags,
    nutrition = nutrition,
    nSteps = nSteps,
    steps = steps,
    description = description,
    ingredients = ingredients,
    nIngredients = nIngredients,
)