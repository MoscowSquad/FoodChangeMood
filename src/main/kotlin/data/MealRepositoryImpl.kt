package org.example.data

import org.example.logic.MealRepository
import org.example.model.Meal
import org.example.utils.CustomParser

class MealRepositoryImpl(
    private val parser: CustomParser
) : MealRepository {
    override fun getAllMeals(): List<Meal> = parser.parseMealsCsv()
}