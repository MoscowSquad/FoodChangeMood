package data

import logic.FoodRepository
import model.Food
import utils.CsvUtils
import java.io.File
import java.time.LocalDate

class CsvFood(private val csvPath: String) : FoodRepository {
    override fun getFoodByDate(date: LocalDate): List<Food> {
        val reader = File(csvPath).bufferedReader()
        reader.readLine()

        val meals = mutableListOf<Food>()
        var currentMeal = StringBuilder()
        var quoteCount = 0

        reader.lineSequence().forEach { line ->
            currentMeal.appendLine(line)
            quoteCount += line.count { it == '"' }

            if (quoteCount % 2 == 0) {
                val fullLine = currentMeal.toString()
                val submitted = LocalDate.parse(CsvUtils.extractField(fullLine, 4))

                if (submitted == date) {
                    meals.add(CsvUtils.toFood(fullLine))
                }

                currentMeal.clear()
                quoteCount = 0
            }
        }

        return meals
    }
}
