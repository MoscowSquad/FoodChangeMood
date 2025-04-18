package data

import org.example.logic.MealRepository
import org.example.model.Meal

class CsvFileData(
    //private val readLines: ReadLines,
    private val foodFileParser: FoodFileParser,
):MealRepository {


    override fun getAllMeals(): List<Meal> {
val allMeals = mutableListOf<Meal>()
      // readLines.readFoodFileLines().forEach{
         allMeals.add(foodFileParser.parseOneLine())
        return allMeals
       }

    }

