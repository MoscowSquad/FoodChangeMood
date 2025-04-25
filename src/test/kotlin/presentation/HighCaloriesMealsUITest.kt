package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.usecases.GetHighCaloriesMealsUseCase
import org.example.model.Exceptions
import org.example.presentation.HighCaloriesMealsUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HighCaloriesMealsUITest {
    private lateinit var getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var highCaloriesMealsUITest: HighCaloriesMealsUI

    @BeforeEach
    fun setup() {
        getHighCaloriesMealsUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        highCaloriesMealsUITest = HighCaloriesMealsUI(getHighCaloriesMealsUseCase, consoleIO)
    }


    @Test
    fun `should verify calling getting next meal by high-calories-meals-use-case function when getting high-calories-meals`() {
        // Given
        every { consoleIO.read() } returns ""

        // When
        highCaloriesMealsUITest.invoke()

        // Then
        verify {
            getHighCaloriesMealsUseCase.nextMeal()
        }
    }


    @Test
    fun `should display error message when there is not high-calories-meals`() {
        // Given
        every {
            getHighCaloriesMealsUseCase.nextMeal()
        } throws Exceptions.NoMealsFoundException()

        // When
        highCaloriesMealsUITest.invoke()

        // Then
        verify {
            consoleIO.showError("No meals found matching your criteria.")
        }
    }
}