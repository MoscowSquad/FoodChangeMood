package org.example.data

import java.io.File

class CsvFoodReader(private val csvFile: File) {
    fun readLinesFromFile(): List<String> {
        return csvFile.readLines()
    }
}