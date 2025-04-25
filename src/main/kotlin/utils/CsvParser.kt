package org.example.utils

import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.Nutrition
import java.util.*
import java.util.stream.Collectors

class CsvParser(
    private val fileParser: CsvFileParser
) {
    fun parseMealsCsv(): List<Meal> {
        val lines = fileParser.getLines()
        return parseFileLinesIntoCSV(lines)
    }

    private fun parseFileLinesIntoCSV(lines: List<String>): List<Meal> {
        return lines.drop(1)
            .take(500)
            .parallelStream()
            .map { line -> parseLine(line.removePrefix("\n")) }
            .filter(Objects::nonNull)
            .map { it!! }
            .collect(Collectors.toList())
    }

    private fun parseLine(line: String): Meal? {
        return try {
            val parts = line.split(CSV_SPLIT_REGEX.toRegex())

            val name = parts.parseString(MealTokens.NAME)
            val id = parts.parseInt(MealTokens.ID)
            val minutes = parts.parseInt(MealTokens.MINUTES)
            val contributorId = parts.parseInt(MealTokens.CONTRIBUTOR_ID)
            val submitted = parts.parseString(MealTokens.SUBMITTED)
            val nSteps = parts.parseInt(MealTokens.N_STEPS)
            val description = parts.parseString(MealTokens.DESCRIPTION)
            val nIngredients = parts.parseInt(MealTokens.N_INGREDIENTS)

            val nutrition = parseNutrition(parts)
            val tags = parseTags(parts)
            val steps = parseSteps(parts)
            val ingredients = parseIngredients(parts)

            Meal(
                name, id, minutes, contributorId, submitted,
                tags, nutrition, nSteps, steps, description, ingredients, nIngredients
            )
        } catch (e: Exceptions.CsvParsingException) {
            null
        }
    }

    private fun List<String>.parseInt(index: Int): Int? {
        if (this.size <= index)
            throw Exceptions.CsvParsingException()
        return getOrNull(index)?.toIntOrNull()
    }

    private fun List<String>.parseString(index: Int): String? {
        if (this.size <= index) throw Exceptions.CsvParsingException()
        return getOrNull(index)?.ifBlank { null }?.removeSurrounding("\"")
    }


    private fun parseNutrition(parts: List<String>): Nutrition? {
        return parts.getOrNull(MealTokens.NUTRITION)
            ?.mapToString()
            ?.map { it.trim().toDoubleOrNull() }
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
        return parts.getOrNull(MealTokens.TAGS)
            ?.mapToString()
            ?.map(::cleanListSurrounding)
    }

    private fun parseSteps(parts: List<String>): List<String>? {
        return parts.getOrNull(MealTokens.STEPS)
            ?.mapToString()
            ?.map(::cleanListSurrounding)
    }

    private fun parseIngredients(parts: List<String>): List<String>? {
        return parts.getOrNull(MealTokens.INGREDIENTS)
            ?.mapToString()
            ?.map(::cleanListSurrounding)
    }

    private fun String?.mapToString(): List<String>? {
        return this
            ?.removePrefix("\"[")
            ?.removeSuffix("]\"")
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.takeIf { it.isNotEmpty() }
    }

    private fun cleanListSurrounding(value: String): String {
        return value.trim().removePrefix("\"[")
            .removeSuffix("]\"")
            .removePrefix("'")
            .removeSuffix("'")
    }

    companion object {
        private const val CSV_SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
    }

}