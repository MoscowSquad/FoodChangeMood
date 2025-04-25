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
    fun `should return more than one meals matching with country`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "B-laban Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt"),
            createMeal(name = "Koshary Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt"),
            createMeal(name = "qushtuta Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals


        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 3 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. B-laban Egypt Meal")
        assertThat(consoleIO.outputs).contains("2. Koshary Egypt Meal")
        assertThat(consoleIO.outputs).contains("3. qushtuta Egypt Meal")
    }

    @Test
    fun `should return one meal matching with country`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "Koshary Egypt Meal", tags = listOf("Egypt"), description = "This meal from Egypt")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals
        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 1 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. Koshary Egypt Meal")
    }

    @Test
    fun `should return one meal matching with country in name`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "Koshary Egypt Meal", tags = listOf("iraq"), description = "This meal from iraq")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals
        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 1 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. Koshary Egypt Meal")
    }

    @Test
    fun `should return one meal matching with country in tag`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "Koshary Meal", tags = listOf("Egypt"), description = "This meal from iraq")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals
        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 1 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. Koshary Meal")
    }

    @Test
    fun `should return one meal matching with country in description`() {
        // Given
        val returnMeals = listOf(
            createMeal(name = "Koshary Meal", tags = listOf("iraq"), description = "This meal from Egypt")
        )

        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns returnMeals
        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("Found 1 meals related to Egypt:")
        assertThat(consoleIO.outputs).contains("1. Koshary Meal")
    }

    @Test
    fun `should return no meal found matching with country`() {
        // Given
        every { searchMealByCountryUseCase.searchMealsByCountry("Egypt") } returns emptyList()
        consoleIO.inputs.add("Egypt")

        // When
        searchMealByCountryUI.invoke()

        // Then
        assertThat(consoleIO.outputs).contains("No meals found for Egypt")
    }

}