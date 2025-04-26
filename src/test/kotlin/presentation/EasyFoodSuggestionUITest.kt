package presentation

import io.mockk.*
import logic.usecases.acceptedMeals
import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.presentation.EasyFoodSuggestionUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EasyFoodSuggestionUITest {
    private lateinit var easyFoodSuggestionUI: EasyFoodSuggestionUI
    private lateinit var easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase
    private lateinit var consoleIO: ConsoleIO

    @BeforeEach
    fun setup() {
        easyFoodSuggestionUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        easyFoodSuggestionUI = EasyFoodSuggestionUI(easyFoodSuggestionUseCase, consoleIO)
    }

    @Test
    fun `should display 10 meal when theres no issues`() {
        // Given
        val meals = acceptedMeals()
        every { easyFoodSuggestionUseCase.suggestTenRandomMeals() } returns meals

        // When
        easyFoodSuggestionUI()

        // Then
        verifySequence {
            consoleIO.write("Finding easy meal to prepare ...")
            easyFoodSuggestionUseCase.suggestTenRandomMeals()
            consoleIO.write("Your order is ready: ")
            // display() is an extension function so we need to verify the meals were displayed
            consoleIO.write("Total number of meals: ${meals.size}")
        }
    }

    @Test
    fun `should No meals found matching the criteria`() {
        // Given
        every { easyFoodSuggestionUseCase.suggestTenRandomMeals() } returns emptyList()

        // When
        easyFoodSuggestionUI()

        // Then
        verifySequence {
            consoleIO.write("Finding easy meal to prepare ...")
            easyFoodSuggestionUseCase.suggestTenRandomMeals()
            consoleIO.write("Your order is ready: ")
            consoleIO.write("No meals found matching the criteria.")
        }
    }
}