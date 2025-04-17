package org.example.model

data class Meal(
    val id: Int,
    val name: String,
    val nutrition: Nutrition?,
    val description: String?
)