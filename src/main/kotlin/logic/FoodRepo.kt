package logic

import org.example.model.Meal


interface FoodRepo {
    fun getAllFood():List<Meal>
}