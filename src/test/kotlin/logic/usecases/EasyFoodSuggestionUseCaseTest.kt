package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.EasyFoodSuggestionUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EasyFoodSuggestionUseCaseTest {
    lateinit var mealRepository: MealRepository
    lateinit var easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase


    @BeforeEach
    fun setup() {
        mealRepository = mockk(relaxed = true)
        easyFoodSuggestionUseCase = EasyFoodSuggestionUseCase(mealRepository)
    }


    @Test
    fun `should return 10 meals when start using use case`() {
        //given
        val expectedListSize = 10
        every { mealRepository.getAllMeals() } returns acceptedMeals()

        //when
        val result = easyFoodSuggestionUseCase.suggestTenRandomMeals()
        //then
        assertEquals(expectedListSize, result.size)
    }

    @Test
    fun `should throw exception when it's null`() {
        //given
        every { mealRepository.getAllMeals() } returns nullMeals()
        //when & then
        assertThrows<Exceptions.NoMealsFoundException> {
            easyFoodSuggestionUseCase.suggestTenRandomMeals()
        }
    }

    @Test
    fun `should throw exception when theres no meals with wanted criteria`() {
        //given
        every { mealRepository.getAllMeals() } returns nonAcceptedMeals()
        //when & then
        assertThrows<Exceptions.NoMealsFoundException> {
            easyFoodSuggestionUseCase.suggestTenRandomMeals()

        }

    }

    @Test
    fun `should return 3 meals when start using use case`() {
        //given
        val expectedListSize = 3
        every { mealRepository.getAllMeals() } returns acceptedThreeMeals()

        //when
        val result = easyFoodSuggestionUseCase.suggestTenRandomMeals()
        //then
        assertEquals(expectedListSize, result.size)
    }

}