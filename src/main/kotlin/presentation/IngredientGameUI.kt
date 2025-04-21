package org.example.presentation

import org.example.logic.usecases.GameResult
import org.example.logic.usecases.GetIngredientMealsUseCase

class IngredientGameUI(
    private val getIngredientMealsUseCase: GetIngredientMealsUseCase
) {
    operator fun invoke() {
        val steps = getIngredientMealsUseCase.prepareGameSteps()
        val answers = mutableListOf<Int>()

        for ((index, step) in steps.withIndex()) {
            println("------------------------------")
            println("Q${index + 1}: ${step.question}")
            step.options.forEachIndexed { i, option ->
                println("${i + 1}- $option")
            }
            print("Choose answer: ")
            val input = readln().toIntOrNull()?.minus(1) ?: -1
            answers.add(input)

            if (input != step.correctIndex) break
        }

        when (val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)) {
            is GameResult.Win -> println("YOU WIN 🏆, you've got ${result.totalPoints} point")
            is GameResult.Lose -> println("Wrong answer!!\nYou've got ${result.totalPoints} point")
        }
    }
}
