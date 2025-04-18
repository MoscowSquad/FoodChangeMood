package org.example.data
import java.util.NoSuchElementException
import org.example.logic.MealRepository
import org.example.data.NoFoodFoundException
import org.example.data.IncorrectMealNameException

class RandomMealNameImpl(
    private val mealRepository: MealRepository = MockDataRepository()
) : RandomMealName {

    override fun getName(): String {
        return try {
            mealRepository.getAllMeals()
                .takeIf { it.isNotEmpty() }
                ?.random()
                ?.name
                ?: throw NoFoodFoundException()
        } catch (e: Exception) {
            throw IncorrectMealNameException()
        }
    }
}