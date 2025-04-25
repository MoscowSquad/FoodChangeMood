package org.example.model

sealed class Exceptions(message: String) : Exception(message) {
    class KeywordNotFoundException(val keyword: String) : Exception("No matches found for the text: $keyword")

    class EmptyKeywordException : Exception("You can't search by empty keyword")
    class EmptyTextException : Exception("You can't search by blank text")
    class InvalidInputException(message: String) : Exception(message)
    class NoMealsFoundException(message: String = "No meals found matching your criteria.") : Exception(message)
    class CsvParsingException(message: String = "No meals found matching your criteria.") : Exception(message)
}