package org.example.utils

import java.io.File

class CustomParser {

    fun getResourceFile(fileName: String): File {
        val resource = object {}.javaClass.classLoader.getResource(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")
        return File(resource.toURI())
    }

    fun parseMealsCsv(file: File): List<Meal> {
        val lines = file.readLines()
        val header = lines.first().split(",")
        return lines.drop(1).mapNotNull { line ->
            try {
                val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())

                val name = parts.getOrNull(0)?.ifBlank { null }
                val id = parts.getOrNull(1)?.toIntOrNull()
                val minutes = parts.getOrNull(2)?.toIntOrNull()
                val contributorId = parts.getOrNull(3)?.toIntOrNull()
                val submitted = parts.getOrNull(4)?.ifBlank { null }

                val tags = parts.getOrNull(5)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }

                val nutritionArray = parts.getOrNull(6)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().toDoubleOrNull() }

                val nutrition = nutritionArray?.let {
                    Nutrition(
                        calories = it.getOrNull(0),
                        totalFat = it.getOrNull(1),
                        sugar = it.getOrNull(2),
                        sodium = it.getOrNull(3),
                        protein = it.getOrNull(4),
                        saturatedFat = it.getOrNull(5),
                        carbohydrates = it.getOrNull(6)
                    )
                }

                val nSteps = parts.getOrNull(7)?.toIntOrNull()
                val steps = parts.getOrNull(8)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }

                val description = parts.getOrNull(9)?.ifBlank { null }

                val ingredients = parts.getOrNull(10)?.removeSurrounding("[", "]")
                    ?.split(",")?.map { it.trim().removeSurrounding("'") }?.takeIf { it.isNotEmpty() }

                val nIngredients = parts.getOrNull(11)?.toIntOrNull()

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

data class Meal(
    val name: String?,
    val id: Int?,
    val minutes: Int?,
    val contributorId: Int?,
    val submitted: String?,
    val tags: List<String>?,
    val nutrition: Nutrition?,
    val nSteps: Int?,
    val steps: List<String>?,
    val description: String?,
    val ingredients: List<String>?,
    val nIngredients: Int?
){
    override fun toString(): String {
        super.toString()
        return """
            id: ${this.id}
            name: ${this.name}
            minutes: ${this.minutes}
            contributor_id: ${this.contributorId}
            submitted: ${this.submitted}
            tags: ${this.tags}
            nutrition: ${this.nutrition}
            n_steps: ${this.nSteps}
            steps: ${this.steps}
            description: ${this.description}
            ingredients: ${this.ingredients}
            n_ingredients: ${this.nIngredients}
            
        """.trimIndent()
    }
}

data class Nutrition(
    val calories: Double?,
    val totalFat: Double?,
    val sugar: Double?,
    val sodium: Double?,
    val protein: Double?,
    val saturatedFat: Double?,
    val carbohydrates: Double?
)
