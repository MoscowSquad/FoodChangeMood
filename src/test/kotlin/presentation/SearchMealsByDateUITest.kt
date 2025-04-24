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
}