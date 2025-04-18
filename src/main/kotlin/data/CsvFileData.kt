package data

import logic.FoodRepo
import org.example.model.Meal

class CsvFileData(
    private val readLines: ReadLines,
    private val foodFileParser: FoodFileParser,
):FoodRepo {
    override fun getAllFood(): List<Meal> {

       return readLines.readFoodFileLines().map {
            foodFileParser.parseOneLine(it)
        }
    }
}