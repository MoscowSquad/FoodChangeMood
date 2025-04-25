package org.example.utils

import java.io.File

class CsvFileParser(
    private val file: File
) {
    fun getLines(): List<String> {
        return file.bufferedReader().readLines()
    }
}