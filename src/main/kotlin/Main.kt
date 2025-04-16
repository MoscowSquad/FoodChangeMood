package org.example

import org.example.logic.userInputGuess
import org.example.utils.CsvParser
import org.example.utils.CsvParserImpl

fun main() {
    try {
        val csvParser: CsvParser = CsvParserImpl()
        val randomLine = csvParser.getRandomFoodLine()
        val (foodName, timeString) = randomLine.split(",").map { it.trim() }
        val actualTime = timeString.toInt()

        userInputGuess().apply {
            userGuessingInput(foodName, actualTime)
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}