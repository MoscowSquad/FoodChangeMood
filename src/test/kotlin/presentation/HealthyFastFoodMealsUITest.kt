package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.presentation.HealthyFastFoodMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class HealthyFastFoodMealsUITest {
    private lateinit var getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var healthyFastFoodMealsUI: HealthyFastFoodMealsUI

    @BeforeEach
    fun setUp() {
        getHealthyFastFoodMealsUseCase = mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        healthyFastFoodMealsUI = HealthyFastFoodMealsUI(getHealthyFastFoodMealsUseCase, consoleIO)
    }

    @Test
    fun `should display meals when meals are available`() {
        // Given
        val meals = listOf(
            createMeal(name = "Salad", minutes = 10),
            createMeal(name = "Sandwich", minutes = 5)
        )
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns meals

        // When
        healthyFastFoodMealsUI.invoke()

        // Then
        verifySequence{
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
            consoleIO.write("")
            consoleIO.write("")
            consoleIO.write("")
        }
    }

    @Test
    fun `should handle empty meals list`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns emptyList()

        // When
        healthyFastFoodMealsUI.invoke()

        // Then
        verifySequence {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
            consoleIO.write("")
            consoleIO.write("")
        }
    }
}