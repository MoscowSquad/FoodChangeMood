package org.example

import org.example.utils.CsvParser
import org.example.logic.UserGuess

fun main() {
        val csvData = CsvParser()
        val randomLineParts = csvData.getRandomFoodLine().split(",")
        val foodName = randomLineParts[0].trim()
        val actualTime = randomLineParts[1].trim().toInt()

        val startGuessing = UserGuess()
        startGuessing.userGuess(foodName , actualTime)
}