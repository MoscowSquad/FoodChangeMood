package org.example.dependencyInjection

import org.example.data.MockDataRepository

import org.example.data.FoodCsvParser
import org.example.data.CsvFoodReader
import org.example.data.CsvFoodRepository
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.MealRepository
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single<MealRepository> { MockDataRepository() }
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