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

        if (mealData.containsKey("Message")) {
            println(mealData["Message"])
            break
        }

        println("Suggested meal: ${mealData["Name"]}")
        println("Description: ${mealData["Description"]}")
        println("Do you like it? (yes/no)")

        val response = scanner.nextLine().trim().lowercase()

        if (response == "yes") {
            println("\nYou liked it! Here's the full meal:")
            println("Name: ${mealData["Name"]}")
            println("Description: ${mealData["Description"]}")
            println("Total Fat (g): ${mealData["TotalFat (g)"]}")
            println("Protein (g): ${mealData["Protein (g)"]}")
            println("saturatedFat (g): ${mealData["saturatedFat (g)"]}")
            println("Carbohydrates (g): ${mealData["Carbohydrates (g)"]}")
            break
        } else {
            println("Okay, finding another meal...\n")
        }
    }
}
