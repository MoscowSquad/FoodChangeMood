package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.Nutrition
import org.example.presentation.GymHelperUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymHelperUITest {

    private lateinit var getGymMealsUseCase: GetGymMealsUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var gymHelperUI: GymHelperUI

    @BeforeEach
    fun setUp() {
        getGymMealsUseCase = mockk<GetGymMealsUseCase>()
        consoleIO = mockk<ConsoleIO>()
        gymHelperUI = GymHelperUI(getGymMealsUseCase, consoleIO)
    }

    @Test
    fun `should display welcome message, prompt for input, and show matching meals and total count when invoked`() {

        val calories = "2000"
        val protein = "100"

        every { consoleIO.read() } returnsMany listOf(calories, protein)
        every { consoleIO.write(any()) } returns Unit

        val meals = listOf(
            createMeal(
                nutrition = Nutrition(
                    calories = 500.0,
                    totalFat = null,
                    sugar = null,
                    sodium = null,
                    protein = 50.0,
                    saturatedFat = null,
                    carbohydrates = null
                )
            )
        )

        every { getGymMealsUseCase.invoke(any()) } returns meals

        gymHelperUI.invoke()

        verifySequence {
            consoleIO.write("--- Gym Helper ---")
            consoleIO.write("Enter calories: ")
            consoleIO.read()
            consoleIO.write("Enter protein: ")
            consoleIO.read()
            consoleIO.write("Meals matching your criteria (Calories: $calories, Protein: $protein g):")
            consoleIO.write("Total matching meals found: ${meals.size}")
        }
    }


    @Test
    fun `should  handles invalid input `() {

        every { consoleIO.read() } returnsMany listOf("invalid", "100")
        every { consoleIO.write(any()) } returns Unit

        gymHelperUI.invoke()

        verify { consoleIO.write(match { it.startsWith("Error: ") }) }
        verify(exactly = 0) { getGymMealsUseCase.invoke(any()) }
    }
}