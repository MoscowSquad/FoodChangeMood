package data

import java.io.File

class ReadLines ( private val csvFile: File){
    fun readFoodFileLines():List<String>{
       return csvFile.readLines()

    }
}