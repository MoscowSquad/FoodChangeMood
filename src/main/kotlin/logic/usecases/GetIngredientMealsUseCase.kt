package org.example.logic.usecases

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import kotlin.random.Random

class GetIngredientMealsUseCase(private val mealRepo: MealRepository) {

    fun prepareGameSteps(): List<GameStep> {
        val allMeals = mealRepo.getAllMeals()
            .shuffled()
            .filter { (it.ingredients?.size ?: 0) > 2 && it.name != null }

        val steps = mutableListOf<GameStep>()
        var mealIndex = 0

        repeat(MAX_QUESTIONS) {
            val optionsList = mutableListOf<Meal>()
            repeat(MAX_OPTIONS) {
                optionsList.add(allMeals[mealIndex++])
                if (mealIndex >= allMeals.size) mealIndex = 0
            }

            val options = optionsList.shuffled()
            val correctIndex = Random.nextInt(MAX_OPTIONS)
            val question = options[correctIndex].name ?: ""
            val optionTexts = options.map { it.ingredients?.get(1) ?: "" }

            steps.add(GameStep(question, optionTexts, correctIndex))
        }

        return steps
    }

    fun evaluateAnswers(steps: List<GameStep>, answers: List<Int>): GameResult {
        var points = 0

        for ((index, step) in steps.withIndex()) {
            val userAnswer = answers.getOrNull(index)
            if (userAnswer == null || userAnswer != step.correctIndex) {
                return GameResult.Lose(points)
            }
            points += 1000
        }

        return GameResult.Win(points)
    }

    companion object {
        const val MAX_QUESTIONS = 15
        const val MAX_OPTIONS = 3
    }
}

data class GameStep(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

sealed class GameResult {
    data class Win(val totalPoints: Int) : GameResult()
    data class Lose(val totalPoints: Int) : GameResult()
}