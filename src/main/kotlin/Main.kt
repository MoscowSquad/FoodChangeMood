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

    FoodChangeMoodConsoleUI(
        getHealthyFastFoodMealsUseCase = getKoin().get(),
        searchMealsByNameUseCase = getKoin().get(),
        getIraqiMealsUseCase = getKoin().get(),
        easyFoodSuggestionUseCase = getKoin().get(),
        randomMealNameUseCase = getKoin().get(),
        sweetsWithNoEggUseCase = getKoin().get(),
        getketoDietMealUseCase = getKoin().get(),
        getMealsByDateUseCase = getKoin().get(),
        getMealByIdUseCase = getKoin().get(),
        gymHelperController = getKoin().get(),
        searchMealByCountryUseCase = getKoin().get(),
        searchMealByNameUseCase = getKoin().get(),
        getIngredientMealsUseCase = getKoin().get(),
        getRandomMealsHavePotatoesUseCase = getKoin().get(),
        getHighCaloriesMealsUseCase = getKoin().get(),
        getSeafoodByProteinContentUseCase = getKoin().get(),
        findItalianMealsForLargeGroupsUseCase = getKoin().get(),
    ).start()
}