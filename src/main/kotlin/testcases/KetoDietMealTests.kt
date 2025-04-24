package org.example.testcases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.mockkStatic
import org.example.presentation.ConsoleFoodChangeMoodUI
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Meal
import org.example.model.Nutrition
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.example.model.Exceptions
import org.example.logic.repository.MealRepository
import org.example.presentation.KetoDietMealHelperUI

class GetKetoDietMealUseCaseTest {
    private lateinit var repository: MealRepository
    private lateinit var useCase: GetKetoDietMealUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetKetoDietMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal should return a valid keto meal`() {
        // Given
        val meal = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.getKetoMeal()

        // Then
        assertThat(result.name).isEqualTo("good morning muffins")
    }

    @Test(expected = Exceptions.MealNotFoundException::class)
    fun `getKetoMeal should throw exception if no meals available`() {
        // Given
        every { repository.getAllMeals() } returns emptyList()

        // When
        useCase.getKetoMeal()
    }

    @Test
    fun `likeMeal should return current meal`() {
        // Given
        val meal = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        every { repository.getAllMeals() } returns listOf(meal)
        useCase.getKetoMeal()

        // When
        val liked = useCase.likeMeal()

        // Then
        assertThat(liked.name).isEqualTo("good morning muffins")
    }

    @Test(expected = Exceptions.MealNotFoundException::class)
    fun `likeMeal should throw if no current meal`() {
        // When
        useCase.likeMeal()
    }

    @Test
    fun `dislikeMeal should return a new meal`() {
        // Given
        val meal1 = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        val meal2 = Meal("2", "dirty broccoli", Nutrition(4.0, 11.0, 10.0))
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        // When
        val first = useCase.getKetoMeal()
        val next = useCase.dislikeMeal()

        // Then
        assertThat(next).isNotEqualTo(first)
    }
}

class ConsoleFoodChangeMoodUIHelperTest {
    private lateinit var useCase: GetKetoDietMealUseCase
    private lateinit var consoleUI: ConsoleFoodChangeMoodUI

    @Before
    fun setUp() {
        mockkStatic("kotlin.io.ConsoleKt")

        val repository = mockk<MealRepository>()
        useCase = GetKetoDietMealUseCase(repository)
        consoleUI = ConsoleFoodChangeMoodUI(useCase)

        val meal = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        every { repository.getAllMeals() } returns listOf(meal)
    }

    @Test
    fun `UI should display meal name only initially`() {
        // Given
        val meal = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        every { useCase.getKetoMeal() } returns meal

        // When
        consoleUI.showKetoMeal()

        // Then
        verify { println("Suggested meal: good morning muffins") }
    }

    @Test
    fun `UI should handle like option correctly`() {
        // Given
        val meal = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        every { useCase.getKetoMeal() } returns meal
        every { useCase.likeMeal() } returns meal

        // When
        consoleUI.handleLike()

        // Then
        verify { println("You liked the meal: good morning muffins") }
    }

    @Test
    fun `UI should handle dislike option correctly`() {
        // Given
        val meal1 = Meal("1", "good morning muffins", Nutrition(9.0, 11.0, 5.0))
        val meal2 = Meal("2", "dirty broccoli", Nutrition(4.0, 11.0, 10.0))
        every { useCase.getKetoMeal() } returns meal1
        every { useCase.dislikeMeal() } returns meal2

        // When
        consoleUI.handleDislike()

        // Then
        verify { println("Suggested meal: dirty broccoli") }
    }

    @Test
    fun `UI should exit on option 4`() {
        // When
        consoleUI.menuLoop()

        // Then
        verify(exactly = 1) { println("See you soon. Stay healthy!") }
    }
}
