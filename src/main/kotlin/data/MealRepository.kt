package data

import org.example.model.Meal

interface MealRepository {
    fun getAllMeals(): List<Meal>
}