package org.example.presentation


import kotlin.system.exitProcess

class ConsoleFoodChangeMoodUI(
    private val healthyFastFoodMealsUI: HealthyFastFoodMealsUI,
    private val searchMealByNameUI: SearchMealByNameUI,
    private val iraqiMealsUI: IraqiMealsUI,
    private val easyFoodSuggestionUI: EasyFoodSuggestionUI,
    private val guessGameUI: GuessGameUI,
    private val sweetsWithNoEggUI: SweetsWithNoEggUI,
    private val ketoDietMealHelperUI: KetoDietMealHelperUI,
    private val searchMealsByDateUI: SearchMealsByDateUI,
    private val gymHelperUI: GymHelperUI,
    private val searchMealByCountryUI: SearchMealByCountryUI,
    private val ingredientGameUI: IngredientGameUI,
    private val iLovePotatoUI: ILovePotatoUI,
    private val highCaloriesMealsUI: HighCaloriesMealsUI,
    private val getSeaFoodMealsUI: GetSeaFoodMealsUI,
    private val findItalianMealsForLargeGroupsUI: FindItalianMealsForLargeGroupsUI
) {
    fun start() {
        println("Welcome to Food Change Mood App")
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = readlnOrNull()?.toIntOrNull()
        when (input) {
            1 -> healthyFastFoodMealsUI()
            2 -> searchMealByNameUI()
            3 -> iraqiMealsUI()
            4 -> easyFoodSuggestionUI()
            5 -> guessGameUI()
            6 -> sweetsWithNoEggUI()
            7 -> ketoDietMealHelperUI()
            8 -> searchMealsByDateUI()
            9 -> gymHelperUI()
            10 -> searchMealByCountryUI()
            11 -> ingredientGameUI()
            12 -> iLovePotatoUI()
            13 -> highCaloriesMealsUI()
            14 -> getSeaFoodMealsUI()
            15 -> findItalianMealsForLargeGroupsUI()
            16 -> {
                println("See you soon 👋")
                exitProcess(0)
            }

            else -> {
                println("Invalid Input")
            }
        }
        presentFeatures()
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
}
