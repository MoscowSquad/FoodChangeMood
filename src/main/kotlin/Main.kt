package org.example

import org.example.logic.KetoDietMealHelper
import org.example.utils.CsvParserImpl
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val parser = CsvParserImpl()
    val helper = KetoDietMealHelper(parser)

    helper.loadMeals("src/main/kotlin/food.csv")

    while (true) {
        val mealData = helper.getKetoMeal()
            println("Suggested meal: ${mealData["Name"]}")
            println("Description: ${mealData["Description"]}")
            println("Do you like it? (yes/no)")

        val response = scanner.nextLine().trim().lowercase()

        if (response == "yes") {
            println("\nYou liked it! Here's the full meal:")
            println("Name: ${mealData["Name"]}")
            println("Description: ${mealData["Description"]}")
            println("Fat (g): ${mealData["Fat (g)"]}")
            println("Protein (g): ${mealData["Protein (g)"]}")
            println("Carbs (g): ${mealData["Carbs (g)"]}")
            break
        } else {
            println("Okay, finding another meal...\n")
        }
    }
}
