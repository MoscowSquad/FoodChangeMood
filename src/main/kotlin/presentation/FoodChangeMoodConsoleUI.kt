package org.example.presentation


import org.example.data.GymHelperController
import org.example.data.GymHelperException
import org.example.exceptions.Exceptions
import org.example.logic.*
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByDateUseCase
import org.example.logic.SearchMealByCountryUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import java.text.SimpleDateFormat
import java.util.*

class FoodChangeMoodConsoleUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase,
    private val searchMealsByCountry: SearchMealByCountryUseCase,
    private val getMealsByDate: GetMealsByDateUseCase,
    private val getMealById: GetMealByIdUseCase,
    private val gymHelperController: GymHelperController,
    private val findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase,
    private val getRandomMealsHavePotatoes: GetRandomMealsHavePotatoes,
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase,
    private val getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase

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
            4 -> searchMealsByDate()
            6 -> sweetsWithNoEgg()
            9 -> gymHelper()
            12 -> iLovePotato()
            13 -> soThinProblem()
            15 -> findItalianMealsForLargeGroups()


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
        println("6- Sweets with No Eggs")
        println("9- Gym Helper")
        println("12- I Love Potato: Show list of 10 meals that include potatoes")
        println("13- So Thin Problem: Suggest a meal with more than 700 calories")
        println("15- Find Italian meals for large groups")

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
    private fun findItalianMealsForLargeGroups() {
        println("\nFinding Italian meals suitable for large groups...")
        val italianMeals = findItalianMealsForLargeGroupsUseCase.invoke()
        if (italianMeals.isEmpty()) {
            println("No Italian meals for large groups found.")
        } else {
            println("\nItalian Meals for Large Groups:")
            displayMeals(italianMeals)
            println("\nTotal Italian meals for large groups found: ${italianMeals.size}")
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
    private fun gymHelper() {
        println("--- Gym Helper ---")
        print("Enter calories: ")
        val caloriesInput = scanner.nextLine()
        print("Enter protein")
        val proteinInput = scanner.nextLine()

        try {
            val matchingMeals = gymHelperController.runGymHelper(caloriesInput, proteinInput)
            println("Meals matching your criteria (Calories: $caloriesInput, Protein: $proteinInput g):")
            displayMeals(matchingMeals)
            println("Total matching meals found: ${matchingMeals.size}")
        } catch (e: GymHelperException) {
            println("Error: ${e.message}")
        }
    }

    private fun sweetsWithNoEgg() {
        println("--- Sweets with No Eggs ---")
        val sweet = sweetsWithNoEggUseCase.getSweetsWithNoEggUseCase()
        if (sweet == null) {
            println("No egg-free sweets available.")
        } else {
            println("Suggested Sweet:")
            displaySingleMeal(sweet)
        }
    }

    private fun displaySingleMeal(meal: Meal) {
        println("Name: ${meal.name}")
        meal.description?.let { println("Description: $it") } ?: println("Description: N/A")
        println("Prep Time: ${meal.minutes} minutes")
        println("Ingredients: ${meal.ingredients?.joinToString() ?: "N/A"}")
        meal.tags?.let { println("Tags: ${it.joinToString()}") } ?: println("Tags: N/A")
        if (meal.nutrition == null) {
            println("There is no nutrition info")
        } else {
            println(
                "Nutrition: " +
                        "Calories: ${meal.nutrition.calories ?: 0}, " +
                        "Total Fat: ${meal.nutrition.totalFat ?: 0}g, " +
                        "Saturated Fat: ${meal.nutrition.saturatedFat ?: 0}g, " +
                        "Carbs: ${meal.nutrition.carbohydrates ?: 0}g, " +
                        "Protein: ${meal.nutrition.protein ?: 0}g"
            )
        }
    }

    private fun iLovePotato() {
        println("-- I Love Potato ---")
        val potatoMeals = getRandomMealsHavePotatoes.getRandomPotatoMeals()
        if (potatoMeals.isEmpty()) {
            println("No meals with potatoes found.")
        } else {
            println("Here are 10 random meals that include potatoes:")
            displayMeals(potatoMeals)
            println("Total meals with potatoes found: ${potatoMeals.size}")
        }
    }

    private fun soThinProblem() {
        println("--- So Thin Problem ---")
        try {
            val highCalorieMeal = getHighCaloriesMealsUseCase.invoke()
            println("Suggested High-Calorie Meal:")
            displaySingleMeal(highCalorieMeal)
        } catch (e: Exceptions.MealNotFoundException) {
            println("No meals with more than 700 calories found.")
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