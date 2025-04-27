package org.example.presentation

import org.example.logic.usecases.FindItalianMealsForLargeGroupsUseCase
import org.example.presentation.io.ConsoleIO
import org.example.utils.display

class FindItalianMealsForLargeGroupsUI(
    private val findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase,
    consoleIO: ConsoleIO
) : ConsoleIO by consoleIO {
    operator fun invoke() {
        write("\nFinding Italian meals suitable for large groups...")
        val italianMeals = findItalianMealsForLargeGroupsUseCase.invoke()
        if (italianMeals.isEmpty()) {
            write("No Italian meals for large groups found.")
        } else {
            write("\nItalian Meals for Large Groups:")
            italianMeals.display()
            write("\nTotal Italian meals for large groups found: ${italianMeals.size}")
        }
    }
}
