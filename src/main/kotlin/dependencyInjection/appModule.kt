package org.example.dependencyInjection


import org.example.data.FoodCsvParser
import org.example.data.CsvFoodReader
import org.example.data.CsvFoodRepository
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.MealRepository
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
    // Data layer
    single { File("food.csv") }
    single { FoodCsvParser() }
    single { CsvFoodReader(get()) }

    // Repository layer
    single<MealRepository> { CsvFoodRepository(get(),get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }


    // UI layer
    single { FoodChangeMoodConsoleUI(get()) }

}