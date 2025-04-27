package logic.usecases

import org.example.model.Nutrition

fun createNutrition(
    calories: Double? = null,
    totalFat: Double? = null,
    sugar: Double? = null,
    sodium: Double? = null,
    protein: Double? = null,
    saturatedFat: Double? = null,
    carbohydrates: Double? = null,
) = Nutrition(
    calories = calories,
    totalFat = totalFat,
    sugar = sugar,
    sodium = sodium,
    protein = protein,
    saturatedFat = saturatedFat,
    carbohydrates = carbohydrates,
)