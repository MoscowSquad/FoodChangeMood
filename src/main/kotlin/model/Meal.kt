package org.example.model

data class Meal(
    val id: Int,
    val name: String,
    val description: String?,
    val ingredients: List<String>,
    val prepTimeMinutes: Int,
    val nutrition: Nutrition
)