package org.example.presentation

import org.example.logic.usecases.GetMealByIdUseCase
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.model.Exceptions
import org.example.presentation.io.ConsoleIO
import org.example.utils.display
import java.text.SimpleDateFormat

class SearchMealsByDateUI(
    private val getMealsByDateUseCase: GetMealsByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd").apply { isLenient = false }

    operator fun invoke() {
        val date = promptForDate() ?: return

        try {
            val meals = getMealsByDateUseCase.getMealsByDate(date)
            if (meals.isEmpty()) throw Exceptions.NoMealsFoundException("No meals found for the selected date.")

            write("\nMeals found:")
            meals.forEach { write("ID: ${it.id}, Name: ${it.name}") }

            val id = promptForMealId() ?: return
            val meal = getMealByIdUseCase.getMealById(id)

            meal.display()
        } catch (e: Exceptions.NoMealsFoundException) {
            write(e.message)
        }
    }

    private fun promptForDate(): String? {
        print("Enter a date in format yyyy-MM-dd: ")
        val input = read()

        return try {
            dateFormat.parse(input)
            input
        } catch (e: Exception) {
            write("Invalid date format. Please use yyyy-MM-dd.")
            null
        }
    }

    private fun promptForMealId(): Int? {
        print("Enter the ID of the meal you want details for: ")
        return try {
            read().toInt()
        } catch (e: NumberFormatException) {
            write("Invalid ID format. Please enter a number.")
            null
        }
    }
}
