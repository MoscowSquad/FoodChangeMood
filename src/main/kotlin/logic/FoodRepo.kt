package logic

import models.Food

interface FoodRepo {
    fun getAllFood():List<Food>
}