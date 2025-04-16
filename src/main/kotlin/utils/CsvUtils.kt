package utils

import model.Food

object CsvUtils {
    fun extractField(line: String, targetIndex: Int): String {
        var index = 0
        var insideQuotes = false
        val current = StringBuilder()

        val chars = line.iterator()
        while (chars.hasNext()) {
            val c = chars.nextChar()
            when {
                c == '"' -> insideQuotes = !insideQuotes
                c == ',' && !insideQuotes -> {
                    if (index == targetIndex) return current.toString()
                    current.clear()
                    index++
                }
                else -> current.append(c)
            }
        }

        return if (index == targetIndex) current.toString() else ""
    }

    fun toFood(line: String): Food {
        return Food(
            name = extractField(line, 0),
            id = extractField(line, 1).toInt(),
            submitted = extractField(line, 4),
            description = extractField(line, 9)
        )
    }

}
