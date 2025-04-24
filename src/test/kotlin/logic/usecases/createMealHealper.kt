package logic.usecases

import org.example.model.Meal
import org.example.model.Nutrition

private val DEFAULT_TAGS = listOf("italian", "main-dish", "pasta", "dinner-party", "oven", "large-groups")
private val DEFAULT_STEPS = listOf(
    "prepare meat sauce with ground beef and tomatoes",
    "cook lasagna noodles until al dente",
    "make bechamel sauce",
    "layer noodles, meat sauce, bechamel, and parmesan in baking dish",
    "repeat layers three times",
    "top with mozzarella",
    "bake at 375°F for 45 minutes",
    "let rest for 15 minutes before serving",
    "slice and serve",
    "garnish with basil",
    "check oven temperature",
    "prepare side salad",
    "set table",
    "pour wine",
    "enjoy"
)
private val DEFAULT_INGREDIENTS = listOf(
    "lasagna noodles",
    "ground beef",
    "tomatoes",
    "onion",
    "garlic",
    "parmesan cheese",
    "mozzarella cheese",
    "milk",
    "butter",
    "flour",
    "olive oil",
    "basil",
    "salt",
    "pepper"
)

fun createMealHelper(
    name: String,
    id: Int,
    minutes: Int = 120,
    contributorId: Int = 101,
    submitted: String = "2024-01-01",
    tags: List<String>? = DEFAULT_TAGS,
    nutrition: Nutrition = defaultNutrition(),
    nSteps: Int = 15,
    steps: List<String> = DEFAULT_STEPS,
    description: String? = "A hearty Italian lasagna perfect for feeding a crowd.",
    ingredients: List<String> = DEFAULT_INGREDIENTS,
    nIngredients: Int = 14
): Meal {
    return Meal(
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
        nIngredients = nIngredients
    )
}

fun defaultNutrition(): Nutrition {
    return Nutrition(
        calories = 650.0,
        totalFat = 35.0,
        sugar = 45.0,
        sodium = 40.0,
        protein = 30.0,
        saturatedFat = 20.0,
        carbohydrates = 15.0
    )
}