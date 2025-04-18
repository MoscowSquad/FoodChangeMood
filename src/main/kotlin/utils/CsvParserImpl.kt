package org.example.utils

import org.example.model.Meal
import org.example.model.Nutrition
import java.io.File

class CsvParserImpl: CsvParser {
    private val csvFile: File = File("src/main/kotlin/food.csv")

    override fun parse(csvPath: String): List<Meal> {
        val file = File(csvPath)
        if (!file.exists()) throw NoSuchElementException("CSV file not found at ${file.absolutePath}")

        val lines = file.readLines().drop(1)
        val meals = mutableListOf<Meal>()

        for ((index, line) in lines.withIndex()) {
            try {
                val cols = Regex(""",(?![^\[]*\])""")
                    .split(line)
                    .map { it.trim() }

                if (cols.size < 12) {
                    continue
                }

                val name = cols.getOrNull(0)?.trim() ?: "Unknown Meal"
                val nutritionStr = cols.getOrNull(6)?.trim() ?: continue
                val description = cols.getOrNull(9)?.trim() ?: "No description"

                // Ensure proper nutritional data mapping here (element 2, 5, 6, 7)
                val nutrition = parseNutrition(nutritionStr)
                if (nutrition.size < 7) {
                    println("Parsed nutrition for line ${index + 2}: $nutrition")
                    //continue
                }

                val totalFat = nutrition.getOrElse(1) { 0.0 }
                val protein = nutrition.getOrElse(4) { 0.0 }
                val saturatedFat = nutrition.getOrElse(5) { 0.0 }
                //val carbohydrates = nutrition.getOrElse(6) { 0.0 }
                val carbohydrates = nutrition.lastOrNull() ?: 0.0

                val meal = Meal(
                    id = index + 1, // assuming id is sequential
                    name = name,
                    minutes = 30, // assuming fixed minutes, update if needed
                    contributorId = 1, // assuming a fixed contributor, update as necessary
                    submitted = 0, // assuming not submitted, update as necessary
                    tags = listOf(), // assuming no tags, update if needed
                    nutrition = Nutrition(totalFat, saturatedFat, 0.0, 0.0, protein, saturatedFat, carbohydrates),
                    nSteps = 1, // assuming only one step, update if needed
                    steps = listOf(description),
                    description = description,
                    ingredients = listOf(), // assuming no ingredients, update as needed
                    nIngredients = 0 // assuming no ingredients
                )

                meals.add(meal)

            } catch (e: Exception) {
                // Handle exceptions if any
            }
        }

        return meals
    }

    //private fun parseNutrition(nutritionStr: String): List<Double> {
     //   return nutritionStr
      //      .removePrefix("[")
      //      .removeSuffix("]")
      //      .split(",")
       //     .mapNotNull { it.trim().toDoubleOrNull() ?: 0.0}
   // }



    private fun parseNutrition(nutritionStr: String): List<Double> {
        println("Raw nutrition string: '$nutritionStr'")  // طباعة القيمة الأصلية

        val cleanedStr = nutritionStr
            .replace("\"", "")
            .replace("[", "")
            .replace("]", "")
            .trim()

        println("Cleaned nutrition string: '$cleanedStr'")  // طباعة بعد التنظيف

        return cleanedStr
            .split(",")
            .mapIndexed { index, value ->
                val trimmed = value.trim()
                val number = trimmed.toDoubleOrNull()
                if (number == null) {
                    println("⚠️ Couldn't parse value at index $index: '$trimmed'")
                }
                number ?: 0.0
            }
    }
}
