package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.model.Exceptions
import org.example.presentation.ILovePotatoUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ILovePotatoUITest {

    private lateinit var getRandomMealsHavePotatoesUseCase: GetRandomMealsHavePotatoesUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var iLovePotatoUI: ILovePotatoUI

    @BeforeEach
    fun setUp() {
        getRandomMealsHavePotatoesUseCase = mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        iLovePotatoUI = ILovePotatoUI(getRandomMealsHavePotatoesUseCase, consoleIO)
    }

    @Test
    fun `should show ten random potato meals when success`() {
        // Given
        every { getRandomMealsHavePotatoesUseCase() } returns (1..10).map {
            createMeal(id = 1, ingredients = listOf("potato"))
        }

        // When
        iLovePotatoUI()

        // Then
        verifySequence {
            getRandomMealsHavePotatoesUseCase.invoke()
            consoleIO.write("\uD83E\uDD54 -- I LOVE POTATO -- \uD83E\uDD54")
            consoleIO.write("Here are some tasty meals with potatoes:")
            consoleIO.write("Total shown: 10")
        }
    }

    @Test
    fun `should show no meals found when no potato meals found`() {
        // Given
        every { getRandomMealsHavePotatoesUseCase() } throws
                Exceptions.NoMealsFoundException("No meals found with potatoes.")

        // When
        iLovePotatoUI()

        // Then
        verifySequence {
            getRandomMealsHavePotatoesUseCase.invoke()
            consoleIO.write("\uD83E\uDD54 -- I LOVE POTATO -- \uD83E\uDD54")
            consoleIO.write("No meals found with potatoes.")

        }
    }
}