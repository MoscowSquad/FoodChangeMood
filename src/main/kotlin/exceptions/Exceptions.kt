package org.example.exceptions
sealed class Exceptions(message: String) : Exception(message) {
    class InvalidDateFormat(message: String) : Exceptions(message)
    class NoMealsFound(message: String) : Exceptions(message)
}