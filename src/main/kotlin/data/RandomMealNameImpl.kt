package org.example.data
import java.util.NoSuchElementException
import org.example.logic.MealRepository
class RandomMealNameImpl(
    private val mealRepository: MealRepository = MockDataRepository()
) : RandomMealName {

    override fun getRandomFoodName(): String {
        return try {
            mealRepository.getAllMeals()
                .takeIf { it.isNotEmpty() } // Check if list is not empty
                ?.random()
                ?.name
                ?: throw NoSuchElementException("No meals available in repository")
        } catch (e: Exception) {
            throw IllegalStateException("Failed to get random meal name", e)
        }
    }
}