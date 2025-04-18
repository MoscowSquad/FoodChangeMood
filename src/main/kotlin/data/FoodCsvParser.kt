package org.example.data

import org.example.model.Meal
import org.example.model.Nutrition

class FoodCsvParser {
    fun parseOneLine(line: String): Meal {
        val mealInfo = line.split(",")
        return Meal(
            id = mealInfo.getInt(ColumnIndex.ID),
            name = mealInfo[ColumnIndex.NAME],
            description = if (mealInfo[ColumnIndex.DESCRIPTION].isNotBlank())
                mealInfo[ColumnIndex.DESCRIPTION] else null,
            ingredients = parseIngredients(mealInfo[ColumnIndex.INGREDIENTS]),
            prepTimeMinutes = mealInfo.getInt(ColumnIndex.PREP_TIME_MINUTES),
            nutrition = parseNutrition(mealInfo[ColumnIndex.NUTRITION])
        )
    }

    private fun parseIngredients(ingredientsString: String): List<String> {
        return ingredientsString.split(";").map { it.trim() }
    }

    private fun parseNutrition(nutritionString: String): Nutrition {
        val nutritionMap = nutritionString.split(";")
            .associate {
                val parts = it.split("=")
                if (parts.size == 2) {
                    parts[0].trim() to parts[1].trim()
                } else {
                    parts[0].trim() to "0.0"
                }
            }

        return Nutrition(
            calories = nutritionMap["calories"]?.toDoubleOrNull() ?: 0.0,
            totalFat = nutritionMap["totalFat"]?.toDoubleOrNull() ?: 0.0,
            sugar = nutritionMap["sugar"]?.toDoubleOrNull() ?: 0.0,
            sodium = nutritionMap["sodium"]?.toDoubleOrNull() ?: 0.0,
            protein = nutritionMap["protein"]?.toDoubleOrNull() ?: 0.0,
            saturatedFat = nutritionMap["saturatedFat"]?.toDoubleOrNull() ?: 0.0,
            carbohydrates = nutritionMap["carbohydrates"]?.toDoubleOrNull() ?: 0.0
        )
    }

    private fun List<String>.getInt(index: Int): Int {
        return this[index].toIntOrNull() ?: 0
    }
}