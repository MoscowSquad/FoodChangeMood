package presentation

import io.mockk.*
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.presentation.KetoDietMealHelperUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KetoDietMealHelperUITest {

    private lateinit var getKetoDietMealUseCase: GetKetoDietMealUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var ketoDietMealHelperUI: KetoDietMealHelperUI

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        getKetoDietMealUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        ketoDietMealHelperUI = KetoDietMealHelperUI(getKetoDietMealUseCase, consoleIO)
    }

    @Test
    fun `should calling get keto meal function`() {
        // Arrange

        // Act
        ketoDietMealHelperUI.invoke()

        // Assert
        verify {
            getKetoDietMealUseCase.getKetoMeal()
        }
    }


    @Test
    fun `should display error message when no meals are found`() {
        // Arrange
        val exception = Exceptions.NoMealsFoundException("No keto meals available")
        every { getKetoDietMealUseCase.getKetoMeal() } throws exception

        // Act
        ketoDietMealHelperUI.invoke()

        // Assert
        verify {
            consoleIO.write("Finding Keto-diet meal...")
            consoleIO.write("No keto meals available")
        }
    }
}
