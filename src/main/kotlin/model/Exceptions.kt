package org.example.model

sealed class Exceptions(message: String) : Exception(message) {
    class InvalidDateFormat(message: String) : Exceptions(message)
    class KeywordNotFoundException(val keyword: String) : Exception("No matches found for the text: $keyword")

    class BlankKeywordException : Exception("You can't search by blank value")
    class InvalidInputException(message: String) : Exception(message)
    class NoMealsFoundException(message: String = "No meals found matching your criteria.") : Exception(message)
}