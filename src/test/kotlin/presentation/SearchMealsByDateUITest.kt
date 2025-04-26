package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import logic.usecases.createMeal
import org.example.logic.usecases.GetMealByIdUseCase
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.presentation.SearchMealsByDateUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMealsByDateUITest {
    private lateinit var getMealsByDateUseCase: GetMealsByDateUseCase
    private lateinit var getMealByIdUseCase: GetMealByIdUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var searchMealsByDateUI: SearchMealsByDateUI

    @BeforeEach
    fun setup() {
        getMealsByDateUseCase = mockk()
        getMealByIdUseCase = mockk()
        consoleIO = mockk(relaxed = true)
        searchMealsByDateUI = SearchMealsByDateUI(getMealsByDateUseCase, getMealByIdUseCase, consoleIO)
    }

    @Test
    fun `should return meals matching with date`() {
        // Given
        val dateInput = "2006-05-04"
        val mealIdInput = "12345"
        val trueMeal = createMeal(id = 12345, name = "Burrito", submitted = "2006-05-04")

        every { consoleIO.read() } returnsMany listOf(dateInput, mealIdInput)
        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns listOf(trueMeal)
        every { getMealByIdUseCase.getMealById(12345) } returns trueMeal

        // When
        searchMealsByDateUI.invoke()

        // Then
        verifySequence {
            consoleIO.read()
            getMealsByDateUseCase.getMealsByDate(dateInput)
            consoleIO.write("\nMeals found:")
            consoleIO.write("ID: 12345, Name: Burrito")
            consoleIO.read()
            getMealByIdUseCase.getMealById(12345)
        }
    }

    @Test
    fun `should throw exception when no meal found`() {
        // Given
        val dateInput = "2006-05-04"

        every { consoleIO.read() } returns dateInput
        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns emptyList()

        // When
        searchMealsByDateUI.invoke()

        // Then
        verifySequence {
            consoleIO.read()
            getMealsByDateUseCase.getMealsByDate(dateInput)
            consoleIO.write("No meals found for the selected date.")
        }
    }

    @Test
    fun `should throw exception when invalid date format`() {
        // Given
        val dateInput = "20060504"

        every { consoleIO.read() } returns dateInput

        // When
        searchMealsByDateUI.invoke()

        // Then
        verify {
            consoleIO.write("Invalid date format. Please use yyyy-MM-dd.")
        }
    }

    @Test
    fun `should show error message when invalid id format`() {
        // Given
        val dateInput = "2006-05-04"
        val invalidIdInput = "abc"
        val trueMeal = createMeal(id = 12345, name = "Burrito", submitted = dateInput)

        every { consoleIO.read() } returnsMany listOf(dateInput, invalidIdInput)
        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns listOf(trueMeal)

        // When
        searchMealsByDateUI.invoke()

        // Then
        verifySequence {
            consoleIO.read()
            getMealsByDateUseCase.getMealsByDate(dateInput)
            consoleIO.write("\nMeals found:")
            consoleIO.write("ID: 12345, Name: Burrito")
            consoleIO.read()
            consoleIO.write("Invalid ID format. Please enter a number.")
        }
    }
}