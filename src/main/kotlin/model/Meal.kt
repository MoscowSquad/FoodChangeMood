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
) {
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

