package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.GetMealByIdUseCase
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.presentation.SearchMealsByDateUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class SearchMealsByDateUITest {
    private lateinit var getMealsByDateUseCase: GetMealsByDateUseCase
    private lateinit var getMealByIdUseCase: GetMealByIdUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var searchMealsByDateUI: SearchMealsByDateUI

    @BeforeEach
    fun setup() {
        getMealsByDateUseCase = mockk()
        getMealByIdUseCase = mockk()
        consoleIO = FakeConsoleIO(LinkedList())
        searchMealsByDateUI = SearchMealsByDateUI(getMealsByDateUseCase, getMealByIdUseCase, consoleIO)

    }

    @Test
    fun `should return meals matching with date`() {
        // Given

        val dateInput = "2006-05-04"
        val mealIdInput = "12345"
        consoleIO.inputs.add(dateInput)
        consoleIO.inputs.add(mealIdInput)

        val trueMeal = createMeal(id = 12345, name = "Burrito", submitted = "2006-05-04")
        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns listOf(trueMeal)
        every { getMealByIdUseCase.getMealById(12345) } returns trueMeal

        // When
        searchMealsByDateUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("\nMeals found:")
        assertThat(consoleIO.outputs).contains("ID: 12345, Name: Burrito")
    }

    @Test
    fun `should throw exception when no meal found`() {
        // Given
        val dateInput = "2006-05-04"

        consoleIO.inputs.add(dateInput)

        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns emptyList()


        // When
        searchMealsByDateUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("No meals found for the selected date.")
    }

    @Test
    fun `should throw exception when invalid date format`() {
        // Given
        val dateInput = "20060504"

        consoleIO.inputs.add(dateInput)

        every { getMealsByDateUseCase.getMealsByDate("2006-05-04") } returns emptyList()


        // When
        searchMealsByDateUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Invalid date format. Please use yyyy-MM-dd.")
    }

    @Test
    fun `should show error message when invalid id format`() {
        // Given
        val dateInput = "2006-05-04"
        val invalidIdInput = "abc"

        consoleIO = FakeConsoleIO(inputs = LinkedList(listOf(dateInput, invalidIdInput)))
        searchMealsByDateUI = SearchMealsByDateUI(getMealsByDateUseCase, getMealByIdUseCase, consoleIO)

        val trueMeal = createMeal(id = 12345, name = "Burrito", submitted = dateInput)
        every { getMealsByDateUseCase.getMealsByDate(dateInput) } returns listOf(trueMeal)

        // When
        searchMealsByDateUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Invalid ID format. Please enter a number.")
    }

}