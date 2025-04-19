package org.example.logic

import org.example.model.Meal

class GetIngredientMealsUseCase(private val mealRepo: MealRepository) {
    fun ingredientGame() {
        var points = 0
        val firstIndex = 0
        val lastIndex = 2
        var continueAsking: Boolean
        val questionsNumber = 15
        var currentQuestionNumber = 1

        val allMeals = mealRepo.getAllMeals()
        val gameMealList = mutableListOf<Meal>()
        val optionsList = mutableListOf<List<String>>()
        println("-- Welcome to ingredient game --")
        println(
            "note: \"This game will show you meal name,\n" +
                    " you will have 3 ingredient options to choose between\n" +
                    " just one of them is correct\""
        )
        println("*****************************************************************")
        while (currentQuestionNumber <= questionsNumber) {


            allMeals
                .filter { it.ingredients != null && it.name != null }
                .shuffled()
                .take(3)
                .forEach { gameMealList.add(it) }
            for (index in firstIndex..lastIndex) {
                optionsList.add(gameMealList[index].ingredients!!)
            }
            continueAsking = showMealAndOptions(
                mealName = gameMealList[firstIndex].name!!,
                correctOption = gameMealList[firstIndex].ingredients!!,
                optionsList = optionsList,
                currentQuestionNumber = currentQuestionNumber,
            )
            if (continueAsking) {
                points += 1000
                currentQuestionNumber++
                println("Great!!!")
                println("+1000 point")
                println("you've got $points point")
            } else {
                println("Wrong answer!!")
                println("you've got $points point")
                return
            }
        }

    }

    private fun showMealAndOptions(
        currentQuestionNumber: Int,
        mealName: String,
        correctOption: List<String>,
        optionsList: List<List<String>>
    ): Boolean {
        val options = optionsList.shuffled()
        println(mealName)
        println("------------------------------")
        for (index in 0..2) println("${index + 1}-${options[index]}")

        print("Q$currentQuestionNumber: choose answer: ")
        val input = readln()
        return options[input.toInt() - 1] == correctOption
    }
}