package org.example.presentation

import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.model.Meal

class FoodChangeMoodConsoleUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    // You can add more use cases here as you implement more features
)
{
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when(input) {
            1 -> launchHealthyFastFoodMeals()
            2 -> launchSearchMealsByName()
            3 -> launchHighProteinMeals()
            else -> { println("Invalid Input") }
        }
        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood App")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1- Get healthy fast food meals (15 minutes or less with low fat and carbs)")
        println("2- Search for meals by name")
        println("3- Get high protein meals for fitness")
        print("here: ")
    }

    private fun launchHealthyFastFoodMeals() {
        println("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        println("\nHealthy Fast Food Meals (15 minutes or less, low fat and carbs):")
        if (healthyMeals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            displayMeals(healthyMeals)
            println("\nTotal healthy fast food meals found: ${healthyMeals.size}")
        }
    }

    private fun launchSearchMealsByName() {
        println("Enter meal name to search: ")
        readlnOrNull()?.let { mealName ->
            println("Search functionality not implemented yet")
            // This would call a SearchMealsByNameUseCase
        } ?: println("Please enter valid input")
    }

    private fun launchHighProteinMeals() {
        println("Getting high protein meals...")
        println("This feature is not implemented yet")
        // This would call a GetHighProteinMealsUseCase
    }

    private fun displayMeals(meals: List<Meal>) {
        meals.forEachIndexed { index, meal ->
            println("\n${index + 1}. ${meal.name}")
            meal.description?.let { println("   Description: $it") } ?: println("   Description: N/A")
            println("   Prep Time: ${meal.prepTimeMinutes} minutes")
            println("   Nutrition: " +
                    "Calories: ${meal.nutrition.calories}, " +
                    "Total Fat: ${meal.nutrition.totalFat}g, " +
                    "Saturated Fat: ${meal.nutrition.saturatedFat}g, " +
                    "Carbs: ${meal.nutrition.carbohydrates}g")
        }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}