package data

import org.example.model.Meal
import utils.CsvParser

class MealRepositoryImpl(private val csvParser: CsvParser) : MealRepository {
    override fun getAllMeals(): List<Meal> {
        return emptyList()
       // csvParser.parseCsv()
    }
}