package data


import org.example.logic.MealRepository
import org.example.model.Meal

class FindItalianMealsForLargeGroupsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val allMeals = getAllMeals()
        val filteredMeals = filterItalianAndGroupSuitableMeals(allMeals)
        return filteredMeals.ifEmpty { provideFallbackMeals(allMeals) }
    }

    private fun getAllMeals(): List<Meal> {
        return mealRepository.getAllMeals()
    }

    private fun filterItalianAndGroupSuitableMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { meal ->
            isItalianMeal(meal) && isGroupSuitableMeal(meal)
        }
    }

    private fun isItalianMeal(meal: Meal): Boolean {
        val tagsLower = meal.tags.map { it.lowercase() }
        return tagsLower.contains("italian") || meal.description?.lowercase()?.contains("italian")?:false
    }

    private fun isGroupSuitableMeal(meal: Meal): Boolean {
        val tagsLower = meal.tags.map { it.lowercase() }
        return tagsLower.contains("for-large-groups") ||
                tagsLower.contains("dinner-party") ||
                tagsLower.contains("potluck")
    }

    private fun provideFallbackMeals(meals: List<Meal>): List<Meal> {
        return meals
            .filter { meal -> isItalianMeal(meal) }
            .map { meal -> addScalabilityNote(meal) }
    }

    private fun addScalabilityNote(meal: Meal): Meal {
        return meal.copy(
            description = "${meal.description}\n(Note: This dish can be scaled up for large groups by increasing ingredient quantities.)"
        )
    }
}