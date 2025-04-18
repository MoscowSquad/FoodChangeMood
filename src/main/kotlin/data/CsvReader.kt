package org.example.data

import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader

class CsvReader(
    private val csvFile: File
) {
    fun read(): List<Array<String>> = CSVReader(FileReader(csvFile)).readAll()
}