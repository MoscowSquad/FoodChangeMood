package presentation

import io.mockk.mockk
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.presentation.SearchMealsByDateUI
import org.junit.jupiter.api.BeforeEach

class SearchMealsByDateUITest {
    private lateinit var getMealsByDateUseCase: GetMealsByDateUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var searchMealsByDateUI: SearchMealsByDateUI

    @BeforeEach
    fun setup() {
        getMealsByDateUseCase = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)
        searchMealsByDateUI = mockk(relaxed = true)
    }

}