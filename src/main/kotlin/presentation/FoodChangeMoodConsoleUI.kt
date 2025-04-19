package org.example.presentation


import logic.EasyFoodSuggestionUseCase
import org.example.data.GymHelperController
import org.example.logic.*
import org.example.model.Exceptions
import org.example.model.Meal
import java.text.SimpleDateFormat
import java.util.*

class FoodChangeMoodConsoleUI(
    private val getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase, //1
    private val searchMealsByNameUseCase: SearchMealByNameUseCase, //2
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase, //3
    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase, //4
    private val randomMealNameUseCase: RandomMealNameProvider, //5
    private val sweetsWithNoEggUseCase: SweetsWithNoEggUseCase, //6
    private val getketoDietMealUseCase: GetKetoDietMealUseCase, //7
    private val getMealsByDateUseCase: GetMealsByDateUseCase, //8
    private val getMealByIdUseCase: GetMealByIdUseCase, //8
    private val gymHelperController: GymHelperController, //9
    private val searchMealByCountryUseCase: SearchMealByCountryUseCase, //10
    private val searchMealByNameUseCase: SearchMealByNameUseCase, //10
    private val getIngredientMealsUseCase: GetIngredientMealsUseCase, //11
    private val getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase, //12
    private val getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase, //13
    private val getSeafoodByProteinContentUseCase: GetSeafoodByProteinContentUseCase, //14
    private val findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase, //15

) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private val scanner = Scanner(System.`in`)
    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        var shouldExit = false
        when (input) {
            1 -> launchHealthyFastFoodMeals()
            2 -> launchSearchMealsByName()
            3 -> launchGetIraqiMeals()
            4 -> launchEasyFoodSuggestion()
            5 -> launchGuessGame()
            6 -> launchSweetsWithNoEgg()
            7 -> launchKetoDietMealHelper()
            8 -> launchSearchMealsByDate()
            9 -> launchGymHelper()
            10 -> launchExploreOtherCountries()
            11 -> launchIngredientGame()
            12 -> launchILovePotato()
            13 -> launchHighCaloriesMeals()
            14 -> launchListOfAllSeafoodMeals()
            15 -> launchFindItalianMealsForLargeGroups()
            16 -> shouldExit = true

            else -> {
                println("Invalid Input")
            }
        }
        if (shouldExit)
            println("See you soon 👋")
        else
            presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood App")
    }

    private fun showOptions() {
        println("\n=== Please enter one of the following numbers ===")
        println("1- Get healthy fast food meals (15 minutes or less with low fat and carbs)")
        println("2- Search for meals by name")
        println("3- Get iraqi meals")
        println("4- Get easy food suggestion")
        println("5- Guess Game (You will guess meal preparation time)")
        println("6- Get Sweets with no eggs")
        println("7- Keto Diet Meal Helper")
        println("8- Search foods by add-date")
        println("9- Gym Helper (Get your desired amount of calories and protein)")
        println("10- Explore other countries (Get 20 randomly other countries' meals)")
        println("11- Ingredient Game (Guesses meal Ingredients)")
        println("12- I LOVE POTATO (Show list of 10 meals that include potatoes)")
        println("13- Get high-calorie meals (+700cal)")
        println("14- List seafood meals sorted by protein content")
        println("15- Find Italian meals for large groups")
        println("16- Exit")

        print("Enter your option: ")
    }

    private fun launchHealthyFastFoodMeals() {
        println("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        val healthyMeals = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        println("Your order is ready: ")
        if (healthyMeals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            displayMeals(healthyMeals)
            println("Total number of meals: ${healthyMeals.size}")
        }
    }

    private fun launchSearchMealsByName() {
        print("Enter meal name to search: ")
        readlnOrNull()?.let { mealName ->
            try {
                searchMealByNameUseCase.search(mealName).also { displayMeal(it) }
            } catch (e: Exceptions.KeywordNotFoundException) {
                println(e.message)
            } catch (e: Exceptions.BlankKeywordException) {
                println("Please enter valid input")
            }
        } ?: println("Please enter valid input")

        goBack()
    }

    private fun launchHighCaloriesMeals() {
        println("Getting high protein meals...")
        println("This feature is not implemented yet")
        // This would call a GetHighProteinMealsUseCase
    }

    fun exploreFoodCulture() {
        print("Enter a country name to explore its food culture: ")
        val country = readln()
        val meals = searchMealByCountryUseCase.searchMealsByCountry(country)

        if (meals.isEmpty()) {
            println("No meals found for $country")
        } else {
            println("Found ${meals.size} meals related to $country:")
            meals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name}")
            }
        }
    }

    private fun launchFindItalianMealsForLargeGroups() {
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

    private fun launchSearchMealsByDate() {
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
            val meals = getMealsByDateUseCase.getMealsByDate(dateInt)
            println("Meals found:")
            meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

            println("Enter the ID of the meal you want details for:")
            val id = scanner.nextLine().toInt()
            val meal = getMealByIdUseCase.getMealById(id)
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

    private fun launchGymHelper() {
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
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun launchSweetsWithNoEgg() {
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

    private fun launchILovePotato() {
        println("-- I Love Potato ---")
        val potatoMeals = getRandomMealsHavePotatoesUseCase.getRandomPotatoMeals()
        if (potatoMeals.isEmpty()) {
            println("No meals with potatoes found.")
        } else {
            println("Here are 10 random meals that include potatoes:")
            displayMeals(potatoMeals)
            println("Total meals with potatoes found: ${potatoMeals.size}")
        }
    }

    private fun launchSoThinProblem() {
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
        meals.forEach { meal ->
            displayMeal(meal)
        }
        goBack()
    }

    private fun displayMeal(meal: Meal) {
        println("\n${meal.id}. ${meal.name}")
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

    private fun goBack() {
        print("Press ENTER to go back... ")
        readlnOrNull()
    }

    private fun launchListOfAllSeafoodMeals() {

    }

    private fun launchIngredientGame() {

    }

    private fun launchExploreOtherCountries() {

    }

    private fun launchKetoDietMealHelper() {

    }

    private fun launchGuessGame() {

    }

    private fun launchEasyFoodSuggestion() {

    }

    private fun launchGetIraqiMeals() {

    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
