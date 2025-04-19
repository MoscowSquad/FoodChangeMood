package logic

import org.example.logic.MealRepository
import org.example.model.Meal

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {
    fun suggestTenRandomMeals(): List<Meal> {
        return mealRepository.getAllMeals()
            .filter { currentMeal ->
                currentMeal.minutes != null
                        && currentMeal.nIngredients != null
                        && currentMeal.nSteps != null
                        && currentMeal.name != null &&
                        currentMeal.minutes <= 30 && currentMeal.nIngredients <= 5 && currentMeal.nSteps <= 6
            }
            .shuffled()
            .take(10)
    }
}