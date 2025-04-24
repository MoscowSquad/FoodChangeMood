package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecases.createMeal
import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.Nutrition
import org.example.model.NutritionRequest
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
    fun `should  displays welcome message and prompts for input when it called`() {

        every { consoleIO.read() } returnsMany listOf("2000", "100")
        every { consoleIO.write(any()) } returns Unit

        gymHelperUI.invoke()

        verify { consoleIO.write("--- Gym Helper ---") }
        verify { consoleIO.write("Enter calories: ") }
        verify { consoleIO.write("Enter protein: ") }
    }


    @Test
    fun `should GetGymMealsUseCase with correct NutritionRequest when called`() {

        val calories = "2000"
        val protein = "100"
        every { consoleIO.read() } returnsMany listOf(calories, protein)
        every { consoleIO.write(any()) } returns Unit
        val expectedNutrition = NutritionRequest(calories.toDouble(), protein.toDouble())
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
                ),
            )
        )
        every { getGymMealsUseCase.invoke(expectedNutrition) } returns meals

        gymHelperUI.invoke()

        verify { getGymMealsUseCase.invoke(expectedNutrition) }
    }

    @Test
    fun `invoke displays matching meals and total count`() {

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
                ),
            ),

            )
        every { getGymMealsUseCase.invoke(any()) } returns meals


        gymHelperUI.invoke()


        verify { consoleIO.write("Meals matching your criteria (Calories: $calories, Protein: $protein g):") }
        verify { consoleIO.write("Total matching meals found: ${meals.size}") }
    }

    @Test
    fun `invoke handles invalid input gracefully`() {

        every { consoleIO.read() } returnsMany listOf("invalid", "100")
        every { consoleIO.write(any()) } returns Unit

        gymHelperUI.invoke()


        verify { consoleIO.write(match { it.startsWith("Error: ") }) }
        verify(exactly = 0) { getGymMealsUseCase.invoke(any()) }
    }

    @Test
    fun `should handles exception from GetGymMealsUseCase`() {

        val calories = "2000"
        val protein = "100"
        every { consoleIO.read() } returnsMany listOf(calories, protein)
        every { consoleIO.write(any()) } returns Unit
        every { getGymMealsUseCase.invoke(any()) } throws RuntimeException("Use case error")

        gymHelperUI.invoke()

        verify { consoleIO.write("Error: Use case error") }
    }
}
