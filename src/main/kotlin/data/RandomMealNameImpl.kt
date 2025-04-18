package org.example.data
import org.example.logic.MealRepository
import org.example.model.Meal
import org.example.model.NoFoodFoundException
import org.example.model.IncorrectMealNameException

class RandomMealNameImpl(
    private val mealRepository: MealRepository = MockDataRepository()
) : RandomMealName {

    override fun getName(): String {
        return try {
            val meals = mealRepository.getAllMeals().validateMeals()
            meals.randomMeal().validateName()
        } catch (e: NoFoodFoundException) {
            throw e
        } catch (e: Exception) {
            throw IncorrectMealNameException(
                message = "Failed to retrieve meal name: ${e.message}",
            )
        }
    }

    private fun List<Meal>?.validateMeals(): List<Meal> {
        return this?.takeIf { it.isNotEmpty() }
            ?: throw NoFoodFoundException("No meals available in repository")
    }

    private fun List<Meal>.randomMeal(): Meal {
        return random().also {
            if (it.name.isBlank()) {
                throw NoFoodFoundException("Found meal with blank name")
            }
        }
    }

    private fun Meal.validateName(): String {
        return name.takeIf { it.isNotBlank() }
            ?: throw IncorrectMealNameException("Meal name is blank")
    }
}