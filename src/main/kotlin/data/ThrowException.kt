package org.example.data

class NoFoodFoundException(message: String = "No meals available in the repository") :
    RuntimeException(message)

class IncorrectMealNameException(message: String = "Invalid meal name found in repository") :
    IllegalArgumentException(message)