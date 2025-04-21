package org.example.utils

import org.example.model.Meal

fun Meal.display() {
    println("\n${this.id}. ${this.name}")
    this.description?.let { println("   Description: $it") } ?: println("   Description: N/A")
    println("   Prep Time: ${this.minutes} minutes")
    if (this.nutrition == null) {
        println(" There is no nutrition's")
    } else {
        println(
            "   Nutrition: " +
                    "Calories: ${this.nutrition.calories ?: 0}, " +
                    "Total Fat: ${this.nutrition.totalFat ?: 0}g, " +
                    "Saturated Fat: ${this.nutrition.saturatedFat ?: 0}g, " +
                    "Carbs: ${this.nutrition.carbohydrates ?: 0}g"
        )
    }
}

fun List<Meal>.display() {
    this.forEach {
        it.display()
    }
}