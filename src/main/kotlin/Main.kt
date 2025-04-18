package org.example

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.logic.MealsRepository
import org.example.logic.MealsRepositoryImpl
import org.example.usecases.SweetsWithNoEggUseCase
import java.io.File

fun main() {
    val csvReader = CsvReader(File("food.csv"))
    val csvParser = CsvParser()
    val mealsRepository: MealsRepository = MealsRepositoryImpl(
        csvParser = csvParser,
        csvReader = csvReader
    )
    val meals = mealsRepository.getAllMeals()
    println(SweetsWithNoEggUseCase(meals).invoke())
}