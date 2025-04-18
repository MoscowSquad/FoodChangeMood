package org.example.presentation


import org.example.exceptions.Exceptions
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByDateUseCase
import org.example.logic.SearchMealByCountryUseCase
import org.example.model.Meal
import java.text.SimpleDateFormat
import java.util.*

class FoodChangeMoodConsoleUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    private val searchMealsByCountry: SearchMealByCountryUseCase,
    private val getMealsByDate: GetMealsByDateUseCase,
    private val getMealById: GetMealByIdUseCase
) {


    fun start() {
        showWelcome()
        presentFeatures()
    }

    private val scanner = Scanner(System.`in`)
    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        when (input) {
            1 -> launchHealthyFastFoodMeals()
            2 -> launchSearchMealsByName()
            3 -> launchHighProteinMeals()
            else -> {
                println("Invalid Input")
            }
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
        println("4- Search meals by date (yyyyMMdd)")
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

    fun exploreFoodCulture() {
        print("Enter a country name to explore its food culture: ")
        val country = readln()
        val meals = searchMealsByCountry.searchMealsByCountry(country)

        if (meals.isEmpty()) {
            println("No meals found for $country")
        } else {
            println("Found ${meals.size} meals related to $country:")
            meals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name}")
            }
        }
    }

    private fun searchMealsByDate() {
        println("Enter a date in format yyyyMMdd:")
        val input = scanner.nextLine()
        val dateInt = try {
            val format = SimpleDateFormat("yyyyMMdd")
            format.isLenient = false
            format.parse(input)
            input.toInt()
        } catch (e: Exception) {
            throw Exceptions.InvalidDateFormat("Invalid date format. Use yyyyMMdd.")
        }

        try {
            val meals = getMealsByDate.getMealsByDate(dateInt)
            println("Meals found:")
            meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

            println("Enter the ID of the meal you want details for:")
            val id = scanner.nextLine().toInt()
            val meal = getMealById.getMealById(id)
            if (meal != null) {
                println("\n--- Meal Details ---")
                println("Name: ${meal.name}")
                println("Description: ${meal.description}")
                println("Ingredients: ${meal.ingredients?.joinToString()}")
            } else {
                println("Meal not found.")
            }
        } catch (e: Exceptions.NoMealsFound) {
            println(e.message)
        }
    }

    private fun displayMeals(meals: List<Meal>) {
        meals.forEachIndexed { index, meal ->
            println("\n${index + 1}. ${meal.name}")
            meal.description?.let { println("   Description: $it") } ?: println("   Description: N/A")
            println("   Prep Time: ${meal.minutes} minutes")
            if (meal.nutrition == null) {
                println(" There is no nutritions")
            } else {
                println(
                    "   Nutrition: " +
                            "Calories: ${meal.nutrition.calories ?: 0}, " +
                            "Total Fat: ${meal.nutrition.totalFat ?: 0}g, " +
                            "Saturated Fat: ${meal.nutrition.saturatedFat ?: 0}g, " +
                            "Carbs: ${meal.nutrition.carbohydrates ?: 0}g"
                )
            }
        }
    }


    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}