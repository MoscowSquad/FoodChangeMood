package org.example.presentation

import kotlin.system.exitProcess

class ConsoleFoodChangeMoodUI(
    private val healthyFastFoodMealsUI: HealthyFastFoodMealsUI,
    private val searchMealByNameUI: SearchMealByNameUI,
    private val iraqiMealsUI: IraqiMealsUI,
    private val easyFoodSuggestionUI: EasyFoodSuggestionUI,
    //private val guessGameUI: GuessGameUI,
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
        println(
            """
╔════════════════════════════════════════════╗
║      Welcome to Food Change Mood App!      ║
╚════════════════════════════════════════════╝
            """.trimIndent()
        )
        menuLoop()
    }

    private fun menuLoop() {
        while (true) {
            showOptions()
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> healthyFastFoodMealsUI()
                2 -> searchMealByNameUI()
                3 -> iraqiMealsUI()
                4 -> easyFoodSuggestionUI()
                //5 -> guessGameUI()
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
                    println("\n👋 See you soon. Stay healthy!")
                    exitProcess(0)
                }

                else -> println("\n❌ Invalid input. Please enter a number between 1 and 16.")
            }
        }
    }

    private fun showOptions() {
        println(
            """
            
🔸 === Choose an Option === 🔸
1  - 🍔 Healthy Fast Food (Low fat, low carbs, <15 min)
2  - 🔍 Search Meals by Name
3  - 🇮🇶 Get Iraqi Meals
4  - 🍳 Easy Food Suggestions
5  - 🎮 Guess Game (Guess preparation time)
6  - 🍬 Sweets with No Eggs
7  - 🥩 Keto Diet Meal Helper
8  - 🗓️ Search Meals by Date
9  - 🏋️ Gym Helper (Calories & Protein)
10 - 🌍 Explore Meals from Other Countries
11 - 🧠 Ingredient Guessing Game
12 - 🥔 I LOVE POTATO (Top 10 Potato Meals)
13 - 🔋 High-Calorie Meals (700+ cal)
14 - 🐟 Seafood Meals Sorted by Protein
15 - 🍝 Italian Meals for Large Groups
16 - ❎ Exit

Enter your option: 
""".trimIndent()
        )
    }
}
