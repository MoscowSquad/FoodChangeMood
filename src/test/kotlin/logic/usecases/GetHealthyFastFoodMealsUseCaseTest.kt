package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetHealthyFastFoodMealsUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GetHealthyFastFoodMealsUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var getHealthyFastFoodMealsUseCase: GetHealthyFastFoodMealsUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk(relaxed = true)
        getHealthyFastFoodMealsUseCase = GetHealthyFastFoodMealsUseCase(mealRepository)
    }

    @Test
    fun `should return sorted fast to prepare meals`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "Salad", minutes=10),
            createMeal(name = "Sandwich", minutes =  5),
            createMeal(name = "Pasta", minutes =  20),
            createMeal(name = "Soup", minutes =  15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal(name = "Sandwich", minutes =  5),
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Soup", minutes =  15)
        )
    }

    @Test
    fun `should filter out meals with preparation time greater than 15 minutes`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Burger", minutes =  16),
            createMeal(name = "Soup", minutes =  15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Soup", minutes =  15)
        )
    }

    @Test
    fun `should filter out meals without preparation time`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Sandwich", minutes =  null),
            createMeal(name = "Pizza", minutes =  15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal(name = "Salad", minutes =  10),
            createMeal(name = "Pizza", minutes =  15)
        )
    }

    @Test
    fun `should throw exception when no meals match criteria`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "Pasta", minutes =  20),
            createMeal(name = "Roast Beef", minutes =  45),
            createMeal(name = "Sandwich", minutes = null)
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
        }
    }

    @Test
    fun `should throw exception when repository returns empty list`() {
        // Given
        every { mealRepository.getAllMeals() } returns emptyList<Meal>()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [15, 14, 10, 5, 1])
    fun `should include meals with preparation time less than or equal to max time`(minutes: Int) {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal("Quick Meal", minutes=minutes)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal("Quick Meal", minutes=minutes),
        )
    }
}