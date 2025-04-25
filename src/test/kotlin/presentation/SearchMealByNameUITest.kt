package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.example.presentation.SearchMealByNameUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMealByNameUITest {
    private lateinit var searchMealByNameUseCase: SearchMealByNameUseCase
    private lateinit var consoleIO: ConsoleIO
    private lateinit var searchMealByNameUI: SearchMealByNameUI

    @BeforeEach
    fun setup() {
        searchMealByNameUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        searchMealByNameUI = SearchMealByNameUI(searchMealByNameUseCase, consoleIO)
    }


    @Test
    fun `should verify calling search function when search meal by name`() {
        // Given
        every { consoleIO.read() } returns ""

        // When
        searchMealByNameUI.invoke()

        // Then
        verify {
            searchMealByNameUseCase.search("")
        }
    }


    @Test
    fun `should display error message when keyword not matching meal name`() {
        // Given
        val invalidKeyword = "Random keyword"
        val expectedErrorMessage = "No matches found for the text: $invalidKeyword"

        every {
            searchMealByNameUseCase.search(invalidKeyword)
        } throws Exceptions.KeywordNotFoundException(invalidKeyword)

        every { consoleIO.read() } returns invalidKeyword

        // When
        searchMealByNameUI.invoke()

        // Then
        verify {
            consoleIO.showError(expectedErrorMessage)
        }
    }

    @Test
    fun `should display error message when keyword is empty`() {
        // Given
        val invalidKeyword = ""

        every {
            searchMealByNameUseCase.search(invalidKeyword)
        } throws Exceptions.EmptyKeywordException()

        every { consoleIO.read() } returns invalidKeyword

        // When
        searchMealByNameUI.invoke()

        // Then
        verify {
            consoleIO.showError("Please enter meal name")
        }
    }
}