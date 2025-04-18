package org.example.logic

import org.example.model.Meal

interface MealRepository {
    fun getAllMeals(): List<Meal>
    fun getMealsByDate(date: Int): List<Meal>
    fun getMealById(id: Int): Meal?
}