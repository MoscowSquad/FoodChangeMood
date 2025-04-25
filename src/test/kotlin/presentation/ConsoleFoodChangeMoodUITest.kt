package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.presentation.*
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConsoleFoodChangeMoodUITest {
    private lateinit var healthyFastFoodMealsUI: HealthyFastFoodMealsUI
    private lateinit var searchMealByNameUI: SearchMealByNameUI
    private lateinit var iraqiMealsUI: IraqiMealsUI
    private lateinit var easyFoodSuggestionUI: EasyFoodSuggestionUI
    private lateinit var guessGameUI: GuessGameUI
    private lateinit var sweetsWithNoEggUI: SweetsWithNoEggUI
    private lateinit var ketoDietMealHelperUI: KetoDietMealHelperUI
    private lateinit var searchMealsByDateUI: SearchMealsByDateUI
    private lateinit var gymHelperUI: GymHelperUI
    private lateinit var searchMealByCountryUI: SearchMealByCountryUI
    private lateinit var ingredientGameUI: IngredientGameUI
    private lateinit var iLovePotatoUI: ILovePotatoUI
    private lateinit var highCaloriesMealsUI: HighCaloriesMealsUI
    private lateinit var getSeaFoodMealsUI: GetSeaFoodMealsUI
    private lateinit var findItalianMealsForLargeGroupsUI: FindItalianMealsForLargeGroupsUI
    private lateinit var consoleIO: ConsoleIO
    private lateinit var consoleFoodChangeMoodUI: ConsoleFoodChangeMoodUI

    @BeforeEach
    fun setup() {
        healthyFastFoodMealsUI = mockk(relaxed = true)
        searchMealByNameUI = mockk(relaxed = true)
        iraqiMealsUI = mockk(relaxed = true)
        easyFoodSuggestionUI = mockk(relaxed = true)
        guessGameUI = mockk(relaxed = true)
        sweetsWithNoEggUI = mockk(relaxed = true)
        ketoDietMealHelperUI = mockk(relaxed = true)
        searchMealsByDateUI = mockk(relaxed = true)
        gymHelperUI = mockk(relaxed = true)
        searchMealByCountryUI = mockk(relaxed = true)
        ingredientGameUI = mockk(relaxed = true)
        iLovePotatoUI = mockk(relaxed = true)
        highCaloriesMealsUI = mockk(relaxed = true)
        getSeaFoodMealsUI = mockk(relaxed = true)
        findItalianMealsForLargeGroupsUI = mockk(relaxed = true)
        consoleIO = mockk(relaxed = true)

        consoleFoodChangeMoodUI = ConsoleFoodChangeMoodUI(
            healthyFastFoodMealsUI, searchMealByNameUI, iraqiMealsUI, easyFoodSuggestionUI,
            guessGameUI, sweetsWithNoEggUI, ketoDietMealHelperUI, searchMealsByDateUI, gymHelperUI,
            searchMealByCountryUI, ingredientGameUI, iLovePotatoUI, highCaloriesMealsUI,
            getSeaFoodMealsUI, findItalianMealsForLargeGroupsUI, consoleIO
        )
    }

    @Test
    fun `should call healthyFastFoodMealsUI when the input is 1`() {
        // Given
        val input = "1"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { healthyFastFoodMealsUI.invoke() }
    }

    @Test
    fun `should call searchMealByNameUI when the input is 2`() {
        // Given
        val input = "2"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { searchMealByNameUI.invoke() }
    }

    @Test
    fun `should call iraqiMealsUI when the input is 3`() {
        // Given
        val input = "3"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { iraqiMealsUI.invoke() }
    }

    @Test
    fun `should call easyFoodSuggestionUI when the input is 4`() {
        // Given
        val input = "4"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { easyFoodSuggestionUI.invoke() }
    }

    @Test
    fun `should call guessGameUI when the input is 5`() {
        // Given
        val input = "5"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { guessGameUI.invoke() }
    }

    @Test
    fun `should call sweetsWithNoEggUI when the input is 6`() {
        // Given
        val input = "6"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { sweetsWithNoEggUI.invoke() }
    }

    @Test
    fun `should call ketoDietMealHelperUI when the input is 7`() {
        // Given
        val input = "7"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { ketoDietMealHelperUI.invoke() }
    }

    @Test
    fun `should call searchMealsByDateUI when the input is 8`() {
        // Given
        val input = "8"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { searchMealsByDateUI.invoke() }
    }

    @Test
    fun `should call gymHelperUI when the input is 9`() {
        // Given
        val input = "9"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { gymHelperUI.invoke() }
    }

    @Test
    fun `should call searchMealByCountryUI when the input is 10`() {
        // Given
        val input = "10"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { searchMealByCountryUI.invoke() }
    }

    @Test
    fun `should call ingredientGameUI when the input is 11`() {
        // Given
        val input = "11"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { ingredientGameUI.invoke() }
    }

    @Test
    fun `should call iLovePotatoUI when the input is 12`() {
        // Given
        val input = "12"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { iLovePotatoUI.invoke() }
    }

    @Test
    fun `should call highCaloriesMealsUI when the input is 13`() {
        // Given
        val input = "13"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { highCaloriesMealsUI.invoke() }
    }

    @Test
    fun `should call getSeaFoodMealsUI when the input is 14`() {
        // Given
        val input = "14"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { getSeaFoodMealsUI.invoke() }
    }

    @Test
    fun `should call findItalianMealsForLargeGroupsUI when the input is 15`() {
        // "Given"
        val input = "15"
        every { consoleIO.read() } returns input

        // When
        consoleFoodChangeMoodUI.start(true)

        // Then
        verify { findItalianMealsForLargeGroupsUI.invoke() }
    }
}