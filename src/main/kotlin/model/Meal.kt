package org.example.model

import java.util.Date

data class Meal(
    val id: Int,
    val name: String,
    val minutes: Int,
    val contributorId: Int,
    val submitted: Date,
    val tags: List<String>,
    val nutrition: Nutrition,
    val nSteps: Int,
    val steps: List<String>,
    val description: String?,
    val ingredients: List<String>,
    val nIngredients: Int,
)