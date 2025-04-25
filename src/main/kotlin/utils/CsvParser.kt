package org.example.utils

import org.example.model.Meal
import org.example.model.Nutrition
import java.io.File
import java.util.*
import java.util.stream.Collectors

class CsvParser(
    private val file: File
) {
    fun parseMealsCsv(): List<Meal> {
        val bufferedReader = file.bufferedReader()
        val lines = bufferedReader.readLines()
        return parseFileLinesIntoCSV(lines)
    }

    private fun parseFileLinesIntoCSV(lines: List<String>): List<Meal> {
        return lines.drop(1)
            .parallelStream()
            .map { line -> parseLine(line) }
            .filter(Objects::nonNull)
            .map { it!! }
            .collect(Collectors.toList())
    }

    private fun parseLine(line: String): Meal? {
        return try {
            val parts = line.split(CSV_SPLIT_REGEX.toRegex())

            val name = parts.getOrNull(MealTokens.NAME)?.ifBlank { null }
            val id = parts.getOrNull(MealTokens.ID)?.toIntOrNull()
            val minutes = parts.getOrNull(MealTokens.MINUTES)?.toIntOrNull()
            val contributorId = parts.getOrNull(MealTokens.CONTRIBUTOR_ID)?.toIntOrNull()
            val submitted = parts.getOrNull(MealTokens.SUBMITTED)?.ifBlank { null }
            val nSteps = parts.getOrNull(MealTokens.N_STEPS)?.toIntOrNull()
            val description = parts.getOrNull(MealTokens.DESCRIPTION)?.ifBlank { null }
            val nIngredients = parts.getOrNull(MealTokens.N_INGREDIENTS)?.toIntOrNull()

            val nutrition = parseNutrition(parts)
            val tags = parseTags(parts)
            val steps = parseSteps(parts)
            val ingredients = parseIngredients(parts)

            Meal(
                name, id, minutes, contributorId, submitted,
                tags, nutrition, nSteps, steps, description, ingredients, nIngredients
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun parseIngredients(parts: List<String>): List<String>? {
        return parts.getOrNull(MealTokens.INGREDIENTS)?.removeSurrounding("[", "]")
            ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }
    }

    private fun parseSteps(parts: List<String>): List<String>? {
        return parts.getOrNull(MealTokens.STEPS)?.removeSurrounding("[", "]")
            ?.split(",")?.map { it.trim().removeSurrounding("'") }
    }

    private fun parseNutrition(parts: List<String>): Nutrition? {
        return parts.getOrNull(MealTokens.NUTRITION)
            ?.removeSurrounding("\"[", "]\"")
            ?.split(",")?.map { it.trim().toDoubleOrNull() }
            ?.let {
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
    }

    private fun parseTags(parts: List<String>): List<String>? {
        return parts.getOrNull(MealTokens.TAGS)?.removeSurrounding("[", "]")
            ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }
    }

    companion object {
        private const val CSV_SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
    }

}