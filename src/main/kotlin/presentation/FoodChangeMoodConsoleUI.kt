package org.example.presentation

import org.example.logic.MealRepository
import org.example.exceptions.InvalidDateFormatException
import org.example.exceptions.NoMealsFoundException
import java.text.SimpleDateFormat
import java.util.Scanner


class FoodChangeMoodConsoleUI(
    private val mealRepository: MealRepository
) {
    private val scanner = Scanner(System.`in`)

    fun searchMealsByDate() {
        println("Enter a date in format yyyyMMdd:")
        val input = scanner.nextLine()
        val dateInt = try {
            val format = SimpleDateFormat("yyyyMMdd")
            format.isLenient = false
            val parsedDate = format.parse(input)
            input.toInt()
        } catch (e: Exception) {
            throw InvalidDateFormatException("Invalid date format. Use yyyyMMdd.")
        }

        try {
            val meals = mealRepository.getMealsByDate(dateInt)
            println("Meals found:")
            meals.forEach { println("ID: ${it.id}, Name: ${it.name}") }

            println("Enter the ID of the meal you want details for:")
            val id = scanner.nextLine().toInt()
            val meal = mealRepository.getMealById(id)
            if (meal != null) {
                println("\n--- Meal Details ---")
                println("Name: ${meal.name}")
                println("Description: ${meal.description}")
                println("Ingredients: ${meal.ingredients.joinToString()}")
            } else {
                println("Meal not found.")
            }
        } catch (e: NoMealsFoundException) {
            println(e.message)
        }
    }
}