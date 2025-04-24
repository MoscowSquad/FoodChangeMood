package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetMealByIdUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetMealByIdUseCaseTest
{
    private lateinit var mealRepository: MealRepository
    private lateinit var getMealsByIdUseCase: GetMealByIdUseCase

    @BeforeEach
    fun setup(){
        mealRepository = mockk(relaxed = true)
        getMealsByIdUseCase = GetMealByIdUseCase(mealRepository)
    }

    @Test
    fun `should return one meal matching with id`(){
        val meal1 = createMeal(name = "Pizza" , description = "italian meal" , id = 1234)
        val meal2 = createMeal(name = "Burrito" , description = "mexican meal" , id = 2236)
        val meal3 = createMeal(name = "Waffles" , description = "american meal" , id = 3235)
        // Given
        every { mealRepository.getAllMeals() } returns listOf(meal1 , meal2 , meal3)
        val searchInput = 2236

        // When
        val result = getMealsByIdUseCase.getMealById(searchInput)

        // Then
        assertThat(result).isEqualTo(meal2)
    }
    @Test
    fun `should throw exception when no meal found`(){
        val meal1 = createMeal(name = "Pizza" , description = "italian meal" , id = 1234)
        val meal2 = createMeal(name = "Burrito" , description = "mexican meal" , id = 2236)
        val meal3 = createMeal(name = "Waffles" , description = "american meal" , id = 3235)
        // Given
        every { mealRepository.getAllMeals() } returns listOf(meal1 , meal2 , meal3)

        val searchInput = 4444

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getMealsByIdUseCase.getMealById(searchInput)
        }
    }
    @Test
    fun `should throw exception when no meal is Empty`(){
        // Given
        every { mealRepository.getAllMeals() } returns listOf()

        val searchInput = 4444

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getMealsByIdUseCase.getMealById(searchInput)
        }
    }
    @Test
    fun `should throw exception if meal has null id`() {
        val meal1 = createMeal(name = "Pizza", description = "italian", id = null)
        val meal2 = createMeal(name = "Burrito", description = "mexican", id = null)
        val meal3 = createMeal(name = "Waffles" , description = "american meal" , id = 3235)
        // Given
        every { mealRepository.getAllMeals() } returns listOf(meal1, meal2 , meal3)

        val searchInput = 1234

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getMealsByIdUseCase.getMealById(searchInput)
        }
    }
}
