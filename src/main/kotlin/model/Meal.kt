package org.example.model

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
) : Comparable<Meal> {
    override fun compareTo(other: Meal): Int {
        if (nutrition == null && other.nutrition == null)
            return 0
        else if (nutrition == null)
            return 1
        else if (other.nutrition == null)
            return -1

        val nutrition = (nutrition.totalFat ?: 0.0) +
                (nutrition.saturatedFat ?: 0.0) +
                (nutrition.carbohydrates ?: 0.0)
        val otherNutrition = (other.nutrition.totalFat ?: 0.0) +
                (other.nutrition.saturatedFat ?: 0.0) +
                (other.nutrition.carbohydrates ?: 0.0)
        return if (nutrition == otherNutrition) 0 else if (nutrition > otherNutrition) 1 else -1
    }

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

