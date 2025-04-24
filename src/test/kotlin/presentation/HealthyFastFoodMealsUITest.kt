package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
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
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Sandwich", minutes =  5)
        )
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns meals

        // When
        healthyFastFoodMealsUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        assertThat(consoleIO.outputs).contains("Your order is ready: ")
        assertThat(consoleIO.outputs).contains("Total number of meals: 2")
    }

    @Test
    fun `should handle empty meals list`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns emptyList()

        // When
        healthyFastFoodMealsUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Finding healthy fast food meals that can be prepared in 15 minutes or less...")
        assertThat(consoleIO.outputs).contains("No meals found matching the criteria.")
    }

}