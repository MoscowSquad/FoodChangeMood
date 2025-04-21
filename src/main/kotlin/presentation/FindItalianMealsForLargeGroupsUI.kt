package org.example.presentation

import org.example.logic.usecases.FindItalianMealsForLargeGroupsUseCase
import org.example.utils.display

class FindItalianMealsForLargeGroupsUI(
    private val findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase
) {
    operator fun invoke() {
        println("\nFinding Italian meals suitable for large groups...")
        val italianMeals = findItalianMealsForLargeGroupsUseCase.invoke()
        if (italianMeals.isEmpty()) {
            println("No Italian meals for large groups found.")
        } else {
            println("\nItalian Meals for Large Groups:")
            italianMeals.display()
            println("\nTotal Italian meals for large groups found: ${italianMeals.size}")
        }
    }
}
