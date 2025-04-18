package data


import org.example.logic.MealRepository
import org.example.model.Meal

class FindItalianMealsForLargeGroupsUseCase(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Meal> {
        val allMeals = getAllMeals()
        val filteredMeals = filterItalianAndGroupSuitableMeals(allMeals)
        return filteredMeals
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
        val tagsLower = meal.tags?.map { it.lowercase() }
        if (tagsLower != null) {
            return tagsLower.contains("italian") || meal.description?.lowercase()?.contains("italian") ?: false
        }
        return true
    }

    private fun isGroupSuitableMeal(meal: Meal): Boolean {
        val tagsLower = meal.tags?.map { it.lowercase() }
        if (tagsLower != null) {
            return tagsLower.contains("for-large-groups") ||
                    tagsLower.contains("dinner-party") ||
                    tagsLower.contains("potluck")
        }
        return false
    }


}