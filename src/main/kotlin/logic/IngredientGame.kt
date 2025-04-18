package org.example.logic

import org.example.model.Meal

/*
11- Ingredient Game: Display a meal name and three ingredient options
 (one correct, two incorrect).
The user guesses once. A correct guess earns 1000 points;
 an incorrect guess ends the game.
 The game also ends after 15 correct answers.
  Display the final score at the end.
 */
class IngredientGame(private val mealrepo: MealRepository) {
    fun ingredientGame(){
        var points: Int = 0
        var firstIndex=0
        var lastIndex=2
        var continueAsking = false
        val  questionsNumber=15
        var currentQuestionNumber = 1

        val allMeals = mealrepo.getAllMeals()
        val gameMealList= mutableListOf<Meal>()
        val optionsList= mutableListOf<List<String>>()
        println("-- Welcome to ingredient game --")
        println(
            "note: \"This game will show you meal name,\n" +
                    " you will have 3 ingredient options to choose between\n" +
                    " just one of them is correct\""
        )
        println("*****************************************************************")
        while (currentQuestionNumber<=questionsNumber) {


            allMeals
                .shuffled()
                .take(3)
                .forEach { gameMealList.add(it) }
            for (index in firstIndex..lastIndex) {
                optionsList.add(gameMealList[index].ingredients)
            }
            continueAsking = showMealAndOptions(
                mealName = gameMealList[firstIndex].name,
                correctOption = gameMealList[firstIndex].ingredients,
                optionsList = optionsList,
                currentQuestionNumber = currentQuestionNumber,

            )
            if (continueAsking) {
                points += 1000
                currentQuestionNumber++
                println("Great!!!")
                println("+1000 point")
                println("you've got $points point")

            }else{
                println("Wrong answer!!")
                println("you've got $points point")
                return
            }
        }

    }

    private fun showMealAndOptions(currentQuestionNumber: Int,mealName:String, correctOption:List<String>, optionsList:List<List<String>>):Boolean{
        val options=optionsList.shuffled()
        println(mealName)
        println("------------------------------")
        for (index in 0 .. 2) println("${index+1}-${options[index]}")

        print("Q$currentQuestionNumber: choose answer: ")
        val input= readln()
        return options[input.toInt()-1]==correctOption
    }
}