package org.example.data

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.CsvParser

class MealRepositoryImpl(
    private val parser: CsvParser
) : MealRepository {
    private val meals = mutableListOf<Meal>()
    override fun getAllMeals(): List<Meal> =
        if (meals.isEmpty()) parser.parseMealsCsv().also { meals.addAll(it) } else meals
}