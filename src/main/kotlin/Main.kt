import data.CsvFood
import logic.FoodService
import java.time.LocalDate

fun main() {
    val csvPath = "food.csv"
    val repository = CsvFood(csvPath)
    val service = FoodService(repository)

    print("Enter date (YYYY-MM-DD): ")
    val inputDate = readLine()

    try {
        val date = LocalDate.parse(inputDate)
        val meals = service.getMealsByDate(date)

        if (meals.isEmpty()) {
            println("No meals found.")
            return
        }

        meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

        print("\nEnter ID to view description: ")
        val id = readLine()?.toIntOrNull()

        val desc = id?.let { service.getMealDescriptionById(meals, it) }

        if (desc != null) {
            println("\nDescription: $desc")
        } else {
            println("\nInvalid ID or meal not found.")
        }

    } catch (e: Exception) {
        println("Invalid input. Error: ${e.message}")
    }
}
