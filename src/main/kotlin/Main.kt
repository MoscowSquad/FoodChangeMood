package org.example


import org.example.dependencyInjection.appModule
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import org.example.logic.MealRepository



fun main() {
    startKoin {
        modules(appModule)
    }

    val mealRepository: MealRepository by inject(MealRepository::class.java)
    val ui = FoodChangeMoodConsoleUI(mealRepository)
    ui.searchMealsByDate()


}