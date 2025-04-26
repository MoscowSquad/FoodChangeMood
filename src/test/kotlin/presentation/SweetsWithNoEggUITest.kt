package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.model.Exceptions
import org.example.presentation.SweetsWithNoEggUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SweetsWithNoEggUITest {

    private lateinit var sweetsWithNoEggUseCase: SweetsWithNoEggUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var sweetsWithNoEggUI: SweetsWithNoEggUI

    @BeforeEach
    fun setUp() {
        sweetsWithNoEggUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        sweetsWithNoEggUI = SweetsWithNoEggUI(sweetsWithNoEggUseCase, consoleIO)
    }

    @Test
    fun `should show a random sweet with no egg meal when success`() {
        // Given
        val meal = createMeal(
            ingredients = listOf(),
            description = "sweet"
        )
        every { sweetsWithNoEggUseCase() } returns meal

        // When
        sweetsWithNoEggUI()

        // Then
        verifySequence {
            consoleIO.write("🍬--- Sweets Without Eggs ---🍬")
            consoleIO.write("✨ Recommended Sweet:")
        }
    }

    @Test
    fun `should show no meals found when no potato meals found`() {
        // Given
        every { sweetsWithNoEggUseCase() } throws
                Exceptions.NoMealsFoundException("No sweets found without eggs.")

        // When
        sweetsWithNoEggUI()

        // Then
        verifySequence {
            consoleIO.write("🍬--- Sweets Without Eggs ---🍬")
            consoleIO.write("No sweets found without eggs.")
        }
    }
}