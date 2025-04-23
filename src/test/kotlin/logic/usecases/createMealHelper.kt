package logic.usecases

import org.example.model.Meal


fun createMeal(
    mealName: String,
    tags: List<String>,
    description: String
) = Meal(
    name = mealName,
    id = null,
    minutes = null,
    contributorId = null,
    submitted = null,
    tags = tags,
    nutrition = null,
    nSteps = null,
    steps = null,
    description = description,
    ingredients = null,
    nIngredients = null,
)