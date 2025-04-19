package logic

import org.example.logic.MealRepository

class EasyFoodSuggestionUseCase(private val mealRepository: MealRepository) {
    fun foodSuggestion(): List<String> {
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
            .map { it.name!! }
    }
}