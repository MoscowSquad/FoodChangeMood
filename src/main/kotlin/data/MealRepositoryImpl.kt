package org.example.data

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.CustomParser

class MealRepositoryImpl(
    private val parser: CustomParser
) : MealRepository {
    private val meals = mutableListOf<Meal>()
    override fun getAllMeals(): List<Meal> =
        if (meals.isEmpty()) parser.parseMealsCsv() else meals
}