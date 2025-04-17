package logic

import model.Food

interface FoodRepo {
    fun getAllFood():List<Food>
}