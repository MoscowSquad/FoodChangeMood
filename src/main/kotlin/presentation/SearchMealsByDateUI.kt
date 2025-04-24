package org.example.presentation

import org.example.logic.usecases.GetMealByIdUseCase
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.model.Exceptions
import org.example.utils.display
import java.text.SimpleDateFormat
import java.util.*

class SearchMealsByDateUI(
    private val getMealsByDateUseCase: GetMealsByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase
) {
    private val scanner = Scanner(System.`in`)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd").apply { isLenient = false }

    operator fun invoke() {
        val date = promptForDate() ?: return

        try {
            val meals = getMealsByDateUseCase.getMealsByDate(date)
            if (meals.isEmpty()) throw Exceptions.NoMealsFoundException("No meals found for the selected date.")

            println("\nMeals found:")
            meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

            val id = promptForMealId() ?: return
            val meal = getMealByIdUseCase.getMealById(id)

            meal?.display() ?: println("Meal not found.")
        } catch (e: Exceptions.NoMealsFoundException) {
            println(e.message)
        }
    }

    private fun promptForDate(): String? {
        print("Enter a date in format yyyy-MM-dd: ")
        val input = scanner.nextLine()

        return try {
            dateFormat.parse(input)
            input
        } catch (e: Exception) {
            println("Invalid date format. Please use yyyy-MM-dd.")
            null
        }
    }

    private fun promptForMealId(): Int? {
        print("Enter the ID of the meal you want details for: ")
        return try {
            scanner.nextLine().toInt()
        } catch (e: NumberFormatException) {
            println("Invalid ID format. Please enter a number.")
            null
        }
    }
}
