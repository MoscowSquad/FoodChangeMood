package org.example.logic

import org.example.model.Meal

interface MealRepository {
    fun getAllMeals(): List<Meal>
    fun searchMealsByCountry(country: String): List<Meal>
}