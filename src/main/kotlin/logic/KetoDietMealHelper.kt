package org.example.logic

import org.example.model.Meal
import org.example.model.Nutrition

class KetoDietMealHelper(private val repository: MealRepository) {

    private val suggestedMeals = mutableSetOf<String>()
    private var ketoMeals: List<Meal> = emptyList()

    fun loadMeals(csvPath: String) {
        val allMeals = repository.getAllMeals().filter { isKetoFriendly(it.nutrition) }.shuffled()
    }

    // Check if the meal is keto-friendly based on the nutritional info
    private fun isKetoFriendly(nutrition: Nutrition): Boolean {
        return nutrition.carbohydrates < 10 && (nutrition.totalFat + nutrition.saturatedFat) > nutrition.protein
    }

    // Suggest a keto meal
    fun getKetoMeal(): Map<String,Any> {
        for (meal in ketoMeals) {
            if (meal.name !in suggestedMeals) {
                suggestedMeals.add(meal.name)
                return mapOf(
                    "Name" to (meal.name ?: "No Name"),
                    "Description" to (meal.description ?: "No description"),
                    "TotalFat (g)" to (meal.nutrition.totalFat ?: "No TotalFat (g)"),
                    "Protein (g)" to (meal.nutrition.protein ?: "No Protein (g)"),
                    "SaturatedFat (g)" to (meal.nutrition.saturatedFat ?: "No TSaturatedFat (g)"),
                    "Carbohydrates (g)" to (meal.nutrition.carbohydrates ?: "No Carbohydrates (g)")
                )
            }
        }
        return mapOf("Message" to "No more unique keto meals available.")
    }
}
