package org.example.utils

import java.io.File

class CsvParserImpl(
    private val csvFile: File = File("src/main/kotlin/food.csv")
) : CsvParser {

    override fun getRandomFoodLine(): String {
        if (!csvFile.exists()) throw NoSuchElementException("CSV file not found at ${csvFile.absolutePath}")

        return csvFile.readLines()
            .filter { it.isNotBlank() }
            .also { lines ->
                if (lines.isEmpty()) throw NoSuchElementException("CSV file is empty or contains only blank lines")
            }
            .random()
    }
}