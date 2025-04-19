package org.example.utils

import org.example.model.Meal
import org.example.model.Nutrition
import java.io.File

class CustomParser(
    private val fileName: String
) {

    private fun getResourceFile(): File {
        val resource = object {}.javaClass.classLoader.getResource(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")
        return File(resource.toURI())
    }

    fun parseMealsCsv(): List<Meal> {
        val file = getResourceFile()
        val lines = file.readLines().take(500) // remove in production
        return lines.drop(1).mapNotNull { line ->
            try {
                val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())

                val name = parts.getOrNull(MealTokens.NAME)?.ifBlank { null }
                val id = parts.getOrNull(MealTokens.ID)?.toIntOrNull()
                val minutes = parts.getOrNull(MealTokens.MINUTES)?.toIntOrNull()
                val contributorId = parts.getOrNull(MealTokens.CONTRIBUTOR_ID)?.toIntOrNull()
                val submitted = parts.getOrNull(MealTokens.SUBMITTED)?.ifBlank { null }

                val tags = parts.getOrNull(MealTokens.TAGS)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }

                val nutritionArray = parts.getOrNull(MealTokens.NUTRITION)
                    ?.removeSurrounding("\"[", "]\"")
                    ?.split(",")
                    ?.map { it.trim().toDoubleOrNull() }


                val nutrition = nutritionArray?.let {
                    Nutrition(
                        calories = it.getOrNull(NutritionTokens.CALORIES),
                        totalFat = it.getOrNull(NutritionTokens.TOTAL_FAT),
                        sugar = it.getOrNull(NutritionTokens.SUGAR),
                        sodium = it.getOrNull(NutritionTokens.SODIUM),
                        protein = it.getOrNull(NutritionTokens.PROTEIN),
                        saturatedFat = it.getOrNull(NutritionTokens.SATURATED_FAT),
                        carbohydrates = it.getOrNull(NutritionTokens.CARBOHYDRATES)
                    )
                }

                val nSteps = parts.getOrNull(MealTokens.N_STEPS)?.toIntOrNull()
                val steps = parts.getOrNull(MealTokens.STEPS)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }

                val description = parts.getOrNull(MealTokens.DESCRIPTION)?.ifBlank { null }

                val ingredients = parts.getOrNull(MealTokens.INGREDIENTS)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }

                val nIngredients = parts.getOrNull(MealTokens.N_INGREDIENTS)?.toIntOrNull()

                Meal(
                    name, id, minutes, contributorId, submitted,
                    tags, nutrition, nSteps, steps, description, ingredients, nIngredients
                )
            } catch (e: Exception) {
                null
            }
        }
    }

}