package org.example.logic

import model.Food

/*
11- Ingredient Game: Display a meal name and three ingredient options
 (one correct, two incorrect).
The user guesses once. A correct guess earns 1000 points;
 an incorrect guess ends the game.
 The game also ends after 15 correct answers.
  Display the final score at the end.
 */
class IngredientGame(val foodRepo: FoodRepo) {
    fun ingredientGame(){
        var points: Int = 0
        var firstIndex=0
        var lastIndex=2
        var continueAsking = false
        val  questionsNumber=15
        var currentQuestionNumber = 0

        val allMeals = foodRepo.getAllFood()
        val gameMealList= mutableListOf<Food>()
        while (currentQuestionNumber<questionsNumber) {
            println("-- Welcome to ingredient game --")
            println(
                "note: \"This game will show you meal name,\n" +
                        " you will have 3 ingredient options to choose between\n" +
                        " just one of them is correct\""
            )
            println("*****************************************************************")
            allMeals.filter { it.ingredients != null }
                .shuffled()
                .take(3)
                .forEach { gameMealList.add(it) }
            for (index in firstIndex..lastIndex) {
                continueAsking = showMealAndOptions(
                    mealName = gameMealList[firstIndex].mealName,
                    correctOption = gameMealList[firstIndex].ingredients!!,
                    optionsList = listOf(gameMealList[index].ingredients!!)
                )
            }
            if (continueAsking) {
                points += 1000
                currentQuestionNumber++
            }else{
                break
            }
        }

    }

    private fun showMealAndOptions(mealName:String, correctOption:String, optionsList:List<String>):Boolean{
        val options=optionsList.shuffled()
        println(mealName)
        println("------------------------------")
        for (index in 0 .. 2) println("$index-${options[index]}")

        print("choose answer: ")
        val input= readln()
        return options[input.toInt()]==correctOption
    }
}