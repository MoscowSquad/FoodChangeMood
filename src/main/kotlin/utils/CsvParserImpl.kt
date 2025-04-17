package org.example.utils
import org.example.model.Meal
import java.io.File

class CsvParserImpl: CsvParser{
    private val csvFile: File = File("src/main/kotlin/food.csv")

    override fun parse(csvPath: String): List<Meal> {
        val file = File(csvPath)
        if (!file.exists()) throw NoSuchElementException("CSV file not found at ${file.absolutePath}")

        val lines = file.readLines().drop(1)
        val meals = mutableListOf<Meal>()

        for ((index, line) in lines.withIndex()) {
            try {
                val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())

                if (cols.size < 7) {
                    //println("Skipping line ${index + 2}: Not enough columns.")
                    continue
                }

                val name = cols.getOrNull(0)?.trim() ?: "Unknown Meal"
                val nutritionStr = cols.getOrNull(6)?.trim() ?: continue
                val description = cols.getOrNull(9)?.trim() ?: "No description"

                val nutrition = parseNutrition(nutritionStr)
                val fat = nutrition.getOrElse(1) { 0.0 }
                val protein = nutrition.getOrElse(4) { 0.0 }
                val carbs = nutrition.lastOrNull() ?: 0.0

                meals.add(Meal(name, description, fat, protein, carbs))
                //println("Loaded: $name | Fat: $fat, Protein: $protein, Carbs: $carbs")

            } catch (e: Exception) {
                println("Error parsing line ${index + 2}: ${e.message}")
            }
        }

        return meals
    }

    private fun parseNutrition(nutritionStr: String): List<Double> {
        return nutritionStr
            .removePrefix("[")
            .removeSuffix("]")
            .split(",")
            .mapNotNull { it.trim().toDoubleOrNull() }
    }
}
