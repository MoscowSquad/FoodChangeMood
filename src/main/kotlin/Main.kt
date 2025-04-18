package org.example

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.data.MockDataRepository
import org.example.logic.MealRepository
import org.example.usecases.SweetsWithNoEggUseCase
import java.io.File

fun main() {
    val csvReader = CsvReader(File("food.csv"))
    val csvParser = CsvParser()
    val mealsRepository: MealRepository = MockDataRepository()
    val meals = mealsRepository.getAllMeals()
    println(SweetsWithNoEggUseCase(meals).invoke())
}