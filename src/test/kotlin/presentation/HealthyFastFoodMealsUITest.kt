package org.example.presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class HealthyFastFoodMealsUITest {
    private lateinit var getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase
    private lateinit var healthyFastFoodMealsUI: HealthyFastFoodMealsUI
    private val outputStream = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        getHealthyFastFoodMealsUseCase = mockk(relaxed = true)
        healthyFastFoodMealsUI = HealthyFastFoodMealsUI(getHealthyFastFoodMealsUseCase)
        System.setOut(PrintStream(outputStream))
    }

    @Test
    fun `should display meals when healthy meals are available`() {
        // Given
        val meals = listOf(
            createMeal("Sandwich", 5),
            createMeal("Salad", 10),
            createMeal("Soup", 15)
        )
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns meals

        // When
        healthyFastFoodMealsUI()

        // Then
        verify { getHealthyFastFoodMealsUseCase.getHealthyMeals() }
        val output = outputStream.toString()
        assertThat(output).contains("Finding healthy fast food meals")
        assertThat(output).contains("Your order is ready:")
        assertThat(output).contains("Sandwich")
        assertThat(output).contains("Salad")
        assertThat(output).contains("Soup")
        assertThat(output).contains("Total number of meals: 3")
    }

    @Test
    fun `should display no meals message when no healthy meals are available`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns emptyList()

        // When
        healthyFastFoodMealsUI()

        // Then
        verify { getHealthyFastFoodMealsUseCase.getHealthyMeals() }
        val output = outputStream.toString()
        assertThat(output).contains("Finding healthy fast food meals")
        assertThat(output).contains("Your order is ready:")
        assertThat(output).contains("No meals found matching the criteria.")
    }

    @Test
    fun `should get exception when use case throws NoMealsFound exception`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } throws Exceptions.NoMealsFound()

        // When & Then
        assertThrows<Exceptions.NoMealsFound> {
            healthyFastFoodMealsUI()
        }
        verify { getHealthyFastFoodMealsUseCase.getHealthyMeals() }
    }

    @Test
    fun `should display correct output with exactly one meal`() {
        // Given
        val meals = listOf(createMeal("Quick Sandwich", 5))
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns meals

        // When
        healthyFastFoodMealsUI()

        // Then
        verify { getHealthyFastFoodMealsUseCase.getHealthyMeals() }
        val output = outputStream.toString()
        assertThat(output).contains("Finding healthy fast food meals")
        assertThat(output).contains("Your order is ready:")
        assertThat(output).contains("Quick Sandwich")
        assertThat(output).contains("Total number of meals: 1")
    }

    @Test
    fun `should call use case method exactly once`() {
        // Given
        every { getHealthyFastFoodMealsUseCase.getHealthyMeals() } returns listOf(
            createMeal("Sandwich", 5)
        )

        // When
        healthyFastFoodMealsUI()

        // Then
        verifySequence {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
        }
    }
}