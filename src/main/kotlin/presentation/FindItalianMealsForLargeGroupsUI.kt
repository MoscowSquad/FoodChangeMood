package org.example.presentation

import org.example.logic.usecases.FindItalianMealsForLargeGroupsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class FindItalianMealsForLargeGroupsUI(
    private val findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase,
    private val consoleIO: ConsoleIO
) {
    operator fun invoke() {
        consoleIO.write("\nFinding Italian meals suitable for large groups...")
        val italianMeals = findItalianMealsForLargeGroupsUseCase.invoke()
        if (italianMeals.isEmpty()) {
            consoleIO.write("No Italian meals for large groups found.")
        } else {
            consoleIO.write("\nItalian Meals for Large Groups:")
            italianMeals.display()
            consoleIO.write("\nTotal Italian meals for large groups found: ${italianMeals.size}")
        }
    }
}
