package org.example.presentation

import org.example.exceptions.Exceptions
import org.example.logic.MealRepository
import java.text.SimpleDateFormat
import java.util.Scanner
import org.example.model.Meal


class FoodChangeMoodConsoleUI(
    private val mealRepository: MealRepository
) {
    private val scanner = Scanner(System.`in`)

    fun searchMealsByDate() {
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
            val meals = mealRepository.getMealsByDate(dateInt)
            println("Meals found:")
            meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

            println("Enter the ID of the meal you want details for:")
            val id = scanner.nextLine().toInt()
            val meal = mealRepository.getMealById(id)
            if (meal != null) {
                println("\n--- Meal Details ---")
                println("Name: ${meal.name}")
                println("Description: ${meal.description}")
                println("Ingredients: ${meal.ingredients.joinToString()}")
            } else {
                println("Meal not found.")
            }
        } catch (e: Exceptions.NoMealsFound) {
            println(e.message)
        }
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