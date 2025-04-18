package org.example.logic

import org.example.data.CsvParser
import org.example.data.CsvReader
import org.example.models.Meal

class MealsRepositoryImpl(
    private val csvReader: CsvReader,
    private val csvParser: CsvParser
) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return csvReader.read().mapNotNull {
            csvParser.parseLine(it)
        }
    }
}