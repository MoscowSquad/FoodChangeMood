package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecases.createMeal
import org.example.logic.usecases.GetIraqiMealsUseCase
import org.example.presentation.IraqiMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class IraqiMealsUITest {

    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var iraqiMealsUI: IraqiMealsUI


    @BeforeEach
    fun setup() {
        getIraqiMealsUseCase = mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        iraqiMealsUI = IraqiMealsUI(getIraqiMealsUseCase, consoleIO)
    }


    @Test
    fun `should show no meals message when no iraqi meals found`() {
        // When
        iraqiMealsUI.invoke()
        // Then
        verify { getIraqiMealsUseCase.getIraqiMeals() }
    }

    @Test
    fun `should display iraqi meals list when meals are found`() {
        //given
        every { getIraqiMealsUseCase.getIraqiMeals() } returns listOf(
            createMeal(id = 0, tags = listOf("minutes-or-less", "iraqi", "time-to-make", "course", "cuisine")),
            createMeal(id = 1, tags = listOf("preparation", "georgian", "iraqi", "jewish-sephardi")),
            createMeal(id = 2, tags = listOf("palestinian", "saudi-arabian", "condiments-etc", "iraqi", "asian")),
            createMeal(id = 3, tags = listOf("middle-eastern", "iraqi", "herb-and-spice-mixes", "lebanese", "turkish")),
        )

        //when
        iraqiMealsUI.invoke()

        //then
        verify {
            consoleIO.write("")
            getIraqiMealsUseCase.getIraqiMeals()
            consoleIO.write("")
            consoleIO.write("")
        }
    }
}