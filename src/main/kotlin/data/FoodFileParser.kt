package data

import models.Food
import models.Nutrition
import java.util.*

class FoodFileParser {
    fun parseOneLine(line:String):Food{
        val foodInfo = line.split(",")
       return Food(
            mealName = foodInfo[0],
            mealID = foodInfo[1].toIntOrNull(),
            minutes=foodInfo[2].toIntOrNull(),
             contributorID=foodInfo[3].toIntOrNull(),
             submittedDate=foodInfo[4],
             tags=foodInfo[5],
             nutrition=foodInfo[6],
             stepsNumber=foodInfo[7].toIntOrNull(),
            steps=foodInfo[8],
             description=foodInfo[9],
             ingredients=foodInfo[10],
            ingredientsNumber=foodInfo[11].toIntOrNull(),
        )
    }
}