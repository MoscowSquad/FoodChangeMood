package org.example.logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.repository.MealRepository
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
            createMeal("Salad", 10),
            createMeal("Sandwich", 5),
            createMeal("Pasta", 20),
            createMeal("Soup", 15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal("Sandwich", 5),
            createMeal("Salad", 10),
            createMeal("Soup", 15)
        )
    }

    @Test
    fun `should filter out meals with preparation time greater than 15 minutes`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal("Salad", 10),
            createMeal("Burger", 16),
            createMeal("Soup", 15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal("Salad", 10),
            createMeal("Soup", 15)
        ).inOrder()
    }

    @Test
    fun `should filter out meals without preparation time`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal("Salad", 10),
            createMeal("Sandwich", null),
            createMeal("Pizza", 15)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal("Salad", 10),
            createMeal("Pizza", 15)
        ).inOrder()
    }

    @Test
    fun `should throw exception when no meals match criteria`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal("Pasta", 20),
            createMeal("Roast Beef", 45),
            createMeal("Sandwich", null)
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFound> {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
        }
    }

    @Test
    fun `should throw exception when repository returns empty list`() {
        // Given
        every { mealRepository.getAllMeals() } returns emptyList<Meal>()

        // When & Then
        assertThrows<Exceptions.NoMealsFound> {
            getHealthyFastFoodMealsUseCase.getHealthyMeals()
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [15, 14, 10, 5, 1])
    fun `should include meals with preparation time less than or equal to max time`(minutes: Int) {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal("Quick Meal", minutes)
        )

        // When
        val result = getHealthyFastFoodMealsUseCase.getHealthyMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal("Quick Meal", minutes)
        )
    }
}