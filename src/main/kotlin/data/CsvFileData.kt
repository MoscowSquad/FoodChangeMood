package data

import org.example.logic.FoodRepo
import model.Food

class CsvFileData(
    val readLines: ReadLines,
    val foodFileParser: FoodFileParser,
): FoodRepo {
    override fun getAllFood(): List<Food> {

       return readLines.readFoodFileLines().map {
            foodFileParser.parseOneLine(it)
        }
    }
}