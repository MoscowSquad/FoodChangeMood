package logic

import model.Food
import java.time.LocalDate

interface FoodRepository {
    fun getFoodByDate(date: LocalDate): List<Food>
}
