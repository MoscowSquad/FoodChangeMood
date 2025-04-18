package org.example.logic

import org.example.model.Meal

class GetSeafoodByProteinContent(private val mealRepository: MealRepository) {
    fun getSeafoodMealsByProteinContent(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter(::bySeafood)
            .sortedByDescending { it.nutrition.protein }
    }

    private fun bySeafood(meal: Meal): Boolean {
        val seafoodTags = listOf(
            "seafood", "fish", "shrimp", "salmon", "tuna", "crab",
            "lobster", "cod", "halibut", "trout", "mackerel"
        )
        seafoodTags.forEach { tag ->
            if (meal.tags.contains(tag))
                return true
        }
        return false
    }
}

