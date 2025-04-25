package presentation

import io.mockk.mockk
import org.example.logic.usecases.SearchMealByCountryUseCase
import org.example.presentation.SearchMealByCountryUI
import org.junit.jupiter.api.BeforeEach
import java.util.*

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


}