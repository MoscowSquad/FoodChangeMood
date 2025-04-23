import org.example.model.Meal

fun acceptedMeals(): List<Meal> {
    val mealsList = mutableListOf<Meal>()
    for (index in 1..10) {
        val meal = Meal(
            name = "Easy Meal $index",
            minutes = 20,
            nIngredients = 4,
            nSteps = 3,
            id = index,
            contributorId = null,
            submitted = null,
            tags = listOf("easy", "quick"),
            nutrition = null,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "A simple meal",
            ingredients = listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
        )
        mealsList.add(meal)
    }
    return mealsList
}

fun nullMeals(): List<Meal> {
    val mealsList = mutableListOf<Meal>()
    for (index in 1..10) {
        val meal = Meal(
            name = null,
            minutes = null,
            nIngredients = null,
            nSteps = null,
            id = null,
            contributorId = null,
            submitted = null,
            tags = null,
            nutrition = null,
            steps = null,
            description = null,
            ingredients = null,
        )
        mealsList.add(meal)
    }
    return mealsList
}

fun nonAcceptedMeals(): List<Meal> {
    val mealsList = mutableListOf<Meal>()
    for (index in 1..10) {
        val meal = Meal(
            name = "Easy Meal $index",
            minutes = 50,
            nIngredients = 7,
            nSteps = 8,
            id = index,
            contributorId = null,
            submitted = null,
            tags = listOf("easy", "quick"),
            nutrition = null,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "A simple meal",
            ingredients = listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
        )
        mealsList.add(meal)
    }
    return mealsList
}