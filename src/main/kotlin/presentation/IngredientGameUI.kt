package org.example.presentation

import org.example.logic.usecases.GameResult
import org.example.logic.usecases.GetIngredientMealsUseCase
import org.example.presentation.io.ConsoleIO

class IngredientGameUI(
    private val getIngredientMealsUseCase: GetIngredientMealsUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        val steps = getIngredientMealsUseCase.prepareGameSteps()
        val answers = mutableListOf<Int>()

        for ((index, step) in steps.withIndex()) {
            consoleIO.write("------------------------------")
            consoleIO.write("Q${index + 1}: ${step.question}")
            step.options.forEachIndexed { i, option ->
                consoleIO.write("${i + 1}- $option")
            }
            consoleIO.write("Choose answer: ")
            val input = consoleIO.read().toIntOrNull()?.minus(1) ?: -1
            answers.add(input)

            if (input != step.correctIndex) break
        }

        when (val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)) {
            is GameResult.Win -> consoleIO.write("YOU WIN 🏆, you've got ${result.totalPoints} point")
            is GameResult.Lose -> consoleIO.write("Wrong answer!!\nYou've got ${result.totalPoints} point")
        }
    }
}
