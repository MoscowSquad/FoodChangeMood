package org.example.logic

import org.example.model.Meal
import kotlin.random.Random

class GetIngredientMealsUseCase(private val mealRepo: MealRepository) {
    fun ingredientGame() {
        var points = 0
        var mealIndex = 0
        var currentQuestionNumber = 1

        val allMeals = mealRepo.getAllMeals()
            .shuffled()
            .filter { it.ingredients != null && it.ingredients.size > 2 && it.name != null }

        println("-- Welcome to ingredient game --")
        println(
            "note: \"This game will show you meal name,\n" +
                    " you will have 3 ingredient options to choose between\n" +
                    " just one of them is correct\""
        )
        println("*****************************************************************")
        while (currentQuestionNumber <= MAX_QUESTIONS) {
            val optionsList = mutableListOf<Meal>()
            for (index in 1..MAX_OPTIONS) {
                optionsList.add(allMeals[mealIndex++])
                if (mealIndex >= allMeals.size) mealIndex = 0
            }

            val options = optionsList.shuffled()
            val correctIndex = Random.nextInt(MAX_OPTIONS)
            println("------------------------------")
            println(options[correctIndex].name)
            for (index in options.indices) {
                val option = options[index].ingredients?.get(1) ?: ""
                println("${index + 1}- $option")
            }

            print("Q$currentQuestionNumber: choose answer: ")
            val input = readln()
            if (options[input.toInt() - 1] == options[correctIndex]) {
                points += 1000
                currentQuestionNumber++
            } else {
                println("Wrong answer!!")
                println("you've got $points point")
                return
            }
        }

        println("YOU WIN 🏆, you've got $points point")
    }

    companion object {
        const val MAX_QUESTIONS = 15
        const val MAX_OPTIONS = 3
    }
}