package org.example.utils

import java.io.File

class CsvParser{
        private val csvFile = File("src/main/kotlin/food.csv")
        fun getRandomFoodLine(): String {
            if (!csvFile.exists()) throw NoSuchElementException("CSV file not found")

            return csvFile.readLines()
                .filter { it.isNotBlank() }
                .also { if (it.isEmpty()) throw NoSuchElementException("CSV file is empty") }
                .random()
        }
    }