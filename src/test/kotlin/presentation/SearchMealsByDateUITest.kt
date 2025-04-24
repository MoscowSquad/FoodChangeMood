package presentation

import io.mockk.mockk
import org.example.logic.usecases.GetMealByIdUseCase
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.presentation.SearchMealsByDateUI
import org.junit.jupiter.api.BeforeEach
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


}