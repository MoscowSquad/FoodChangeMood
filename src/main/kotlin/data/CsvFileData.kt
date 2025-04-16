package data

import logic.FoodRepo
import models.Food
import java.io.File

class CsvFileData(
    val readLines: ReadLines,
    val foodFileParser: FoodFileParser,
):FoodRepo {
    override fun getAllFood(): List<Food> {

       return readLines.readFoodFileLines().map {
            foodFileParser.parseOneLine(it)
        }
    }
}