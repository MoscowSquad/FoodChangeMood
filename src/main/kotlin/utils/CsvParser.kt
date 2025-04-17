package org.example.utils
import org.example.model.Meal

interface CsvParser{
    fun parse(csvPath: String): List<Meal>

}
