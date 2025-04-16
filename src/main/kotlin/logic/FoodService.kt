package logic


import model.Food
import java.time.LocalDate

class FoodService(private val repo: FoodRepository) {

    fun getMealsByDate(date: LocalDate): List<Food> {
        return repo.getFoodByDate(date)
    }

    fun getMealDescriptionById(meals: List<Food>, id: Int): String? {
        return meals.find { it.id == id }?.description
    }
}

