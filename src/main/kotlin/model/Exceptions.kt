package org.example.model

sealed class Exceptions(message: String) : Exception(message) {
    class InvalidDateFormat(message: String) : Exceptions(message)
    class NoMealsFound(message: String = "No meals found matching the specified criteria.") : Exceptions(message)
    class MealNotFoundException(message: String = "The requested meal could not be found in the repository.") : Exceptions(message)
    class KeywordNotFoundException(val keyword: String) : Exception("No matches found for the text: $keyword")
    class NoFoodFoundException(message: String = "No meals available in the repository") :
        RuntimeException(message)

    class IncorrectMealNameException(message: String = "Invalid meal name found in repository") :
        IllegalArgumentException(message)

    class BlankKeywordException : Exception("You can't search by blank value")
    class InvalidInputException(message: String) : Exception(message)
    class NoMealsFoundException : Exception("No meals found matching your criteria.")
}