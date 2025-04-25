package presentation

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import logic.usecases.acceptedMeals
import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.presentation.EasyFoodSuggestionUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class EasyFoodSuggestionUITest {
    lateinit var easyFoodSuggestionUI: EasyFoodSuggestionUI
    lateinit var easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase
    lateinit var consoleIO: FakeConsoleIO

    @BeforeEach
    fun setup() {
        easyFoodSuggestionUseCase = mockk(relaxed = true)
        consoleIO = FakeConsoleIO(LinkedList())
        easyFoodSuggestionUI = EasyFoodSuggestionUI(easyFoodSuggestionUseCase, consoleIO)
    }

    @Test
    fun `should display 10 meal when theres no issues`() {
        //given
        val numberOfMeals = 10
        every { easyFoodSuggestionUseCase.suggestTenRandomMeals() } returns acceptedMeals()
        //when
        easyFoodSuggestionUI()
        //then
        Truth.assertThat(consoleIO.outputs).contains("Finding easy meal to prepare ...")
        Truth.assertThat(consoleIO.outputs).contains("Your order is ready: ")
        Truth.assertThat(consoleIO.outputs).contains("Total number of meals: $numberOfMeals")
    }


    @Test
    fun `should No meals found matching the criteria`() {
        //given
        every { easyFoodSuggestionUseCase.suggestTenRandomMeals() } returns emptyList()
        //when
        easyFoodSuggestionUI()
        //then
        Truth.assertThat(consoleIO.outputs).contains("Finding easy meal to prepare ...")
        Truth.assertThat(consoleIO.outputs).contains("Your order is ready: ")
        Truth.assertThat(consoleIO.outputs).contains("No meals found matching the criteria.")
    }


}