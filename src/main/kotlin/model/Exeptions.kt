package org.example.model

class KeywordNotFoundException(val keyword: String) : Exception("No matches found for the text: $keyword")
class BlankKeywordException() : Exception("You can't search by blank value")
class NoFoodFoundException(message: String = "No meals available in the repository") :
    RuntimeException(message)

class IncorrectMealNameException(message: String = "Invalid meal name found in repository") :
    IllegalArgumentException(message)