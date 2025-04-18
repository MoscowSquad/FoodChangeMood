package org.example.data

import org.example.logic.MealRepository
import org.example.model.Meal


class CsvFoodRepository(
    private val csvFoodReader: CsvFoodReader,
    private val foodCsvParser: FoodCsvParser,
): MealRepository {
    override fun getAllMeals(): List<Meal> {
        return csvFoodReader.readLinesFromFile().map {
            foodCsvParser.parseOneLine(it.toString())
        }
    }
}