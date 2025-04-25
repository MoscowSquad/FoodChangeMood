package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.example.model.Nutrition
import org.example.presentation.GetSeaFoodMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class GetSeaFoodMealsUITest {
    private val mockUseCase: GetSeafoodByProteinContentUseCase = mockk()
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var ui: GetSeaFoodMealsUI

    @BeforeEach
    fun setUp() {
        consoleIO = FakeConsoleIO(LinkedList())
        ui = GetSeaFoodMealsUI(mockUseCase, consoleIO)
    }

    @Test
    fun `invoke should handle empty seafood meals list`() {
        // Given
        every { mockUseCase.getSeafoodMealsByProteinContent() } returns emptyList()

        // When
        ui.invoke()

        // Then
        val output = consoleIO.outputs
        assertEquals(2, output.size)
        assertEquals("Finding all seafood meals sorted by protein content...", output[0])
        assertEquals("Your order is ready: ", output[1])
    }

    @Test
    fun `invoke should display error message when no meals found`() {
        // Given
        val errorMessage = "No seafood meals available"
        every { mockUseCase.getSeafoodMealsByProteinContent() } throws
                Exceptions.NoMealsFoundException(errorMessage)

        // When
        ui.invoke()

        // Then
        val output = consoleIO.outputs
        assertEquals(3, output.size)
        assertEquals("Finding all seafood meals sorted by protein content...", output[0])
        assertEquals(errorMessage, output[2])
    }

    @Test
    fun `invoke should maintain proper message sequence`() {
        // Given
        every { mockUseCase.getSeafoodMealsByProteinContent() } returns emptyList()

        // When
        ui.invoke()

        // Then
        val output = consoleIO.outputs
        assertEquals(2, output.size)
        assertEquals("Finding all seafood meals sorted by protein content...", output[0])
        assertEquals("Your order is ready: ", output[1])
    }

    @Test
    fun `invoke should catch IllegalStateException from consoleIO write`() {
        // Given
        val testMeals = listOf(createMeal("Salmon", nutrition = logic.usecases.createNutrition(protein = 25.0)))
        every { mockUseCase.getSeafoodMealsByProteinContent() } returns testMeals

        ui = GetSeaFoodMealsUI(mockUseCase, consoleIO)

        // When
        ui.invoke()

        // Then
        val output = consoleIO.outputs
        assertEquals(2, output.size)
        assertEquals("Finding all seafood meals sorted by protein content...", output[0])
    }
}

fun createNutrition(
    calories: Double? = null,
    totalFat: Double? = null,
    sugar: Double? = null,
    sodium: Double? = null,
    protein: Double? = null,
    saturatedFat: Double? = null,
    carbohydrates: Double? = null,
) = Nutrition(
    calories = calories,
    totalFat = totalFat,
    sugar = sugar,
    sodium = sodium,
    protein = protein,
    saturatedFat = saturatedFat,
    carbohydrates = carbohydrates,
)