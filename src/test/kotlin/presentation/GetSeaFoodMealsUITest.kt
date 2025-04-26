package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.example.presentation.GetSeaFoodMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class GetSeaFoodMealsUITest {
    private lateinit var getSeafoodByProteinContentUseCase: GetSeafoodByProteinContentUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var getSeaFoodMealsUITest: GetSeaFoodMealsUI

    @BeforeEach
    fun setUp() {
        getSeafoodByProteinContentUseCase= mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        getSeaFoodMealsUITest = GetSeaFoodMealsUI(getSeafoodByProteinContentUseCase, consoleIO)
    }

    @Test
    fun `invoke should handle empty seafood meals list`() {
        // Given
        every { getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent() } returns emptyList()

        // When
        getSeaFoodMealsUITest.invoke()

        // Then
        verifySequence {
            consoleIO.write("Finding all seafood meals sorted by protein content...")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
            consoleIO.write("Your order is ready: ")
        }
    }

    @Test
    fun `invoke should display error message when no meals found`() {
        // Given
        val errorMessage = "No seafood meals available"
        every { getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent() } throws
                Exceptions.NoMealsFoundException(errorMessage)

        // When
        getSeaFoodMealsUITest.invoke()

        // Then
        verifySequence {
            consoleIO.write("Finding all seafood meals sorted by protein content...")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
            consoleIO.write("Your order is ready: ")
            consoleIO.write(errorMessage)
        }
    }

    @Test
    fun `invoke should maintain proper message sequence`() {
        // Given
        every { getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent() } returns emptyList()

        // When
        getSeaFoodMealsUITest.invoke()

        // Then
        verifySequence {
            consoleIO.write("Finding all seafood meals sorted by protein content...")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
            consoleIO.write("Your order is ready: ")
        }
    }

    @Test
    fun `invoke should display seafood meals sorted by protein content`() {
        // Given
        val testMeals = listOf(createMeal("Salmon", nutrition = logic.usecases.createNutrition(protein = 25.0)))
        every { getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent() } returns testMeals

        // When
        getSeaFoodMealsUITest.invoke()

        // Then
        verifySequence {
            consoleIO.write("Finding all seafood meals sorted by protein content...")
            getSeafoodByProteinContentUseCase.getSeafoodMealsByProteinContent()
            consoleIO.write("Your order is ready: ")
            // The meals would be displayed here, typically via an extension function
            consoleIO.write("Total number of meals: ${testMeals.size}")
        }
    }
}

