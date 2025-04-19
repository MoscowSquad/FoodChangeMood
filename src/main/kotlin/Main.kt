package org.example

import dependencyInjection.appModule
import org.example.dependencyInjection.useCaseModule
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {

    startKoin {
        modules(appModule, useCaseModule)
    }

//    val parser: CustomParser = getKoin().get()
//    val file = parser.getResourceFile("food.csv")
//    val list = parser.parseMealsCsv(file)

    FoodChangeMoodConsoleUI(
        getHealthyFastFoodMealsUseCase = getKoin().get(),
        searchMealsByName = getKoin().get(),
        getIraqiMeals = getKoin().get(),
        easyFoodSuggestionUseCase = getKoin().get(),
        randomMealNameProvider = getKoin().get(),
        sweetsWithNoEggUseCase = getKoin().get(),
        getketoDietMealHelper = getKoin().get(),
        getMealsByDate = getKoin().get(),
        getMealById = getKoin().get(),
        gymHelperController = getKoin().get(),
        searchMealByCountry = getKoin().get(),
        searchMealByNameUseCase = getKoin().get(),
        ingredientGame = getKoin().get(),
        getRandomMealsHavePotatoes = getKoin().get(),
        getHighCaloriesMealsUseCase = getKoin().get(),
        getSeafoodByProteinContent = getKoin().get(),
        findItalianMealsForLargeGroupsUseCase = getKoin().get(),
    ).start()
}