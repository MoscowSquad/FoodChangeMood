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
        getMealById = getKoin().get(),
        getMealsByDate = getKoin().get(),
        searchMealsByCountry = getKoin().get(),
        gymHelperController = getKoin().get(),
        findItalianMealsForLargeGroupsUseCase = getKoin().get(),
        sweetsWithNoEggUseCase = getKoin().get(),
        getRandomMealsHavePotatoes = getKoin().get(),
        getHighCaloriesMealsUseCase = getKoin().get(),
    ).start()
}