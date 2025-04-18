package data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.example.model.Meal
import org.example.model.Nutrition
import java.io.File


class FoodFileParser (private val csvFile: File){
    fun parseOneLine(): Meal {
        var oneMeal:Meal?=null

            csvReader().open(csvFile) {


            readAllWithHeaderAsSequence().forEach { row ->
                Meal(
                    name = row["name"] ?: "",
                    id = row["id"]?.toIntOrNull() ?: 0,
                    minutes = row["minutes"]?.toIntOrNull() ?: 0,
                    contributorId = row["contributor_id"]?.toIntOrNull() ?: 0,
                    submitted = row["submitted"] ?: "",
                    tags = row["tags"]?.removeSurrounding("[", "]")?.split(",")
                        ?.map { it.trim().removeSurrounding("'", " ") } ?: listOf(),
                    nutrition = constructNutrition(row),
                    nSteps = row["n_steps"]?.toIntOrNull() ?: 0,
                    steps = constructSteps(row),
                    description = row["description"] ?: "",
                    ingredients = constructIngredients(row),
                    nIngredients = row["n_ingredients"]?.toIntOrNull() ?: 0,
                ).also { oneMeal = it }


            }
        }
        return oneMeal!!
    }
    private fun constructNutrition(row:Map<String,String>): Nutrition {

        /*- The Nutrition column contains an array of values in the following order:
         Calories, Total Fat, Sugar, Sodium, Protein, Saturated Fat, and Carbohydrates.*/
        val nutritionInfo= row["nutrition"]?.removeSurrounding("[", "]")?.split(",")?.map { it.trim().toDoubleOrNull() ?: 0.0 } ?: listOf()

        return Nutrition(
            calories = nutritionInfo[0],
            totalFat = nutritionInfo[1],
            sugar = nutritionInfo[2],
            sodium = nutritionInfo[3],
            protein = nutritionInfo[4],
            saturatedFat = nutritionInfo[5],
            carbohydrates = nutritionInfo[6],
        )
    }



    private fun constructSteps(row:Map<String,String>):List<String>{
        val steps=  row["steps"]?.removeSurrounding("[", "]")?.split(",")?.map { it.trim().removeSurrounding("'", " ") } ?: listOf()


        return steps
    }
    private fun constructIngredients(row:Map<String,String>):List<String>{
        val ingredients= row["ingredients"]?.removeSurrounding("[", "]")?.split(",")?.map { it.trim().removeSurrounding("'", " ") } ?: listOf()


        return ingredients
    }
}