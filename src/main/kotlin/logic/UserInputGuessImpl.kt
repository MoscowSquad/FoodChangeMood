package org.example.logic

class UserInputGuessImpl : UserInputGuess{
    override fun guessPreparationTime(foodName: String, actualTime: Int) {
        var attempts = 3

        while (attempts > 0) {
            println("Guess time needed to prepare $foodName (Attempts left: $attempts):")
            val userGuess = readlnOrNull()?.toIntOrNull()

            when {
                userGuess == null -> {
                    println("Please enter a valid number")
                    continue
                }
                userGuess == actualTime -> {
                    println("Correct! $foodName takes exactly $actualTime minutes")
                    return
                }
                userGuess < actualTime -> println("Too low!")
                else -> println("Too high!")
            }
            attempts--
        }
        println("Out of attempts! The correct time was $actualTime minutes")
    }
}