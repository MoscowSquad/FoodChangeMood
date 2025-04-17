package org.example.logic

import model.Food


interface FoodRepo {
    fun getAllFood():List<Food>
}