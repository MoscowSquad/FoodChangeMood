package org.example.logic.repository

import org.example.model.Meal

interface MealRepository {
    fun getAllMeals(): List<Meal>
}