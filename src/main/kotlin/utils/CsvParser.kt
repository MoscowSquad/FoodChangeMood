package utils

import org.example.model.Meal

interface CsvParser{
    fun parseCsv(): List<Meal>
}