package presentation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.FindItalianMealsForLargeGroupsUseCase
import org.example.presentation.FindItalianMealsForLargeGroupsUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FindItalianMealsForLargeGroupsUITest {

    private lateinit var findItalianMealsForLargeGroupsUseCase: FindItalianMealsForLargeGroupsUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var ui: FindItalianMealsForLargeGroupsUI

    @BeforeEach
    fun setUp() {
        findItalianMealsForLargeGroupsUseCase = mockk()
        consoleIO = mockk(relaxed = true)
        ui = FindItalianMealsForLargeGroupsUI(findItalianMealsForLargeGroupsUseCase, consoleIO)
    }

    @Test
    fun `should display message when no Italian meals found`() {
        every { findItalianMealsForLargeGroupsUseCase.invoke() } returns emptyList()

        ui.invoke()

        verifySequence {
            consoleIO.write("\nFinding Italian meals suitable for large groups...")
            consoleIO.write("No Italian meals for large groups found.")
        }
    }

    @Test
    fun `should display Itallian meals and total count when meals are found`() {
        val meals = listOf(
            createMeal(name = "Spaghetti Bolognese", tags = listOf("italian"), nIngredients = 12),
            createMeal(name = "Lasagna", tags = listOf("italian"), nIngredients = 15)
        )
        every { findItalianMealsForLargeGroupsUseCase.invoke() } returns meals

        ui.invoke()
        verifySequence {
            consoleIO.write("\nFinding Italian meals suitable for large groups...")
            consoleIO.write("\nItalian Meals for Large Groups:")
            consoleIO.write("\nTotal Italian meals for large groups found: ${meals.size}")
        }
    }
}
