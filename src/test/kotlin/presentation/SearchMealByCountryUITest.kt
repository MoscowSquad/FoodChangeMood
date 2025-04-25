package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.SearchMealByCountryUseCase
import org.example.presentation.SearchMealByCountryUI
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test

class SearchMealByCountryUITest {
    private lateinit var searchMealByCountryUseCase: SearchMealByCountryUseCase
    private lateinit var searchMealByCountryUI: SearchMealByCountryUI
    private lateinit var consoleIO: FakeConsoleIO

    @BeforeEach
    fun setup() {
        searchMealByCountryUseCase = mockk()
        consoleIO = FakeConsoleIO(LinkedList())
        searchMealByCountryUI = SearchMealByCountryUI(searchMealByCountryUseCase, consoleIO)
    }

    @Test
    fun `should return meals matching with country`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "B-laban Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt"),
            createMeal(name = "B-laban Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt"),
            createMeal(name = "B-laban Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals


        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 3 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. B-laban Egypt Meal")
        assertThat(consoleIO.outputs).contains("2. B-laban Egypt Meal")
        assertThat(consoleIO.outputs).contains("3. B-laban Egypt Meal")
    }


}