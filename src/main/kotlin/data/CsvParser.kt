package org.example.data

import org.example.models.Meal
import org.example.models.Nutrition
import org.example.utils.MealTokens
import org.example.utils.NutritionTokens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CsvParser {

    fun parseLine(tokens: Array<String>): Meal? {
        try {
            with(tokens) {
                return Meal(
                    id = get(MealTokens.ID).toIntOrNull() ?: 0,
                    name = get(MealTokens.NAME).removeSurrounding("\""),
                    minutes = get(MealTokens.MINUTES).toIntOrNull() ?: 0,
                    contributorId = get(MealTokens.CONTRIBUTOR_ID).toIntOrNull() ?: -1,
                    submitted = LocalDate.parse(get(MealTokens.SUBMITTED), DateTimeFormatter.ofPattern("M/d/yyyy")),
                    tags = get(MealTokens.TAGS).toList(),
                    nutrition = getNutrition(get(MealTokens.NUTRITION)),
                    nSteps = get(MealTokens.N_STEPS).toIntOrNull() ?: 0,
                    steps = get(MealTokens.STEPS).toList(),
                    description = get(MealTokens.DESCRIPTION).takeUnless { it.isBlank() },
                    ingredients = get(MealTokens.INGREDIENTS).toList(),
                    nIngredients = get(MealTokens.N_INGREDIENTS).toIntOrNull()
                        ?: get(MealTokens.INGREDIENTS).toList().size
                )
            }
        } catch (e: Exception) {
            println("Error parsing line: ${tokens.joinToString()} - ${e.message}")
            return null
        }
    }


    private fun getNutrition(nutrition: String) =
        nutrition.removeSurrounding("[", "]")
            .split(",")
            .mapNotNull { it.trim().toFloatOrNull() }.takeIf {
                it.size == 7
            }?.let { item ->
                Nutrition(
                    calories = item[NutritionTokens.CALORIES],
                    totalFat = item[NutritionTokens.TOTAL_FAT],
                    sugar = item[NutritionTokens.SUGAR],
                    sodium = item[NutritionTokens.SODIUM],
                    protein = item[NutritionTokens.PROTEIN],
                    saturatedFat = item[NutritionTokens.SATURATED_FAT],
                    carbohydrates = item[NutritionTokens.CARBOHYDRATES]
                )
            }

    private fun String.toList(): List<String> =
        this.removeSurrounding("[", "]").split(",").map { it.trim().lowercase().removeSurrounding("\"") }
            .filter { it.isNotEmpty() }
}