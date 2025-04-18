package logic

/*
4- Easy Food Suggestion:
Like a fun game, this feature suggests 10 random meals that are easy to prepare.
A meal is considered easy if it requires 30 minutes or less,
 has 5 ingredients or fewer,
 and can be prepared in 6 steps or fewer.
 */

class EasyFoodSug (private val foodRepo: FoodRepo){
    fun foodSuggestion():List<String>{
        return foodRepo.getAllFood()
            .filter {currentMeal->
                currentMeal.minutes != null &&  currentMeal.nIngredients != null &&  currentMeal.nSteps != null &&
            currentMeal.minutes <=30 && currentMeal.nIngredients <=5 &&currentMeal.nSteps<=6}
            .shuffled()
            .take(10)
            .map { it.name }
    }
}