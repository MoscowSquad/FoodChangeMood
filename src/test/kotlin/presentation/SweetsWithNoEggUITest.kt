package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.model.Exceptions
import org.example.presentation.SweetsWithNoEggUI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class SweetsWithNoEggUITest {

    private lateinit var sweetsWithNoEggUseCase: SweetsWithNoEggUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var sweetsWithNoEggUI: SweetsWithNoEggUI

    @BeforeEach
    fun setUp() {
        sweetsWithNoEggUseCase = mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        sweetsWithNoEggUI = SweetsWithNoEggUI(sweetsWithNoEggUseCase, consoleIO)
    }

    @Test
    fun `should show a random sweet with no egg meal when success`() {
        // Given
        every { sweetsWithNoEggUseCase() } returns createMeal(
            ingredients = listOf(),
            description = "sweet"
        )

        // When
        sweetsWithNoEggUI()

        // Then
        assertEquals(
            listOf(
                "🍬--- Sweets Without Eggs ---🍬",
                "✨ Recommended Sweet:",
            ), consoleIO.outputs.toList()
        )
    }

    @Test
    fun `should show no meals found when no potato meals found`() {
        // Given
        every { sweetsWithNoEggUseCase() } throws
                Exceptions.NoMealsFoundException("No sweets found without eggs.")

        // When
        sweetsWithNoEggUI()

        // Then
        assertEquals(
            listOf(
                "🍬--- Sweets Without Eggs ---🍬",
                "No sweets found without eggs."
            ), consoleIO.outputs.toList()
        )
    }
}