package org.example.testcases

// Unit tests for GetKetoDietMealUseCase.kt and KetoDietMealHelperUI.kt

package org.example

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.Nutrition
import org.example.presentation.IOHandler
import org.example.presentation.KetoDietMealHelperUI
import org.junit.Before
import org.junit.Test

class GetKetoDietMealUseCaseTest {
    private lateinit var repository: MealRepository
    private lateinit var useCase: GetKetoDietMealUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetKetoDietMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal should return a valid keto meal`() {
        val meal = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        every { repository.getAllMeals() } returns listOf(meal)

        val result = useCase.getKetoMeal()

        assertThat(result.name).isEqualTo("good morning   muffins")
    }

    @Test(expected = Exceptions.MealNotFoundException::class)
    fun `getKetoMeal should throw exception if no meals available`() {
        every { repository.getAllMeals() } returns emptyList()

        useCase.getKetoMeal()
    }

    @Test
    fun `likeMeal should return current meal`() {
        val meal = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0,5.0))
        every { repository.getAllMeals() } returns listOf(meal)
        useCase.getKetoMeal()

        val liked = useCase.likeMeal()

        assertThat(liked.name).isEqualTo("good morning   muffins")
    }

    @Test(expected = Exceptions.MealNotFoundException::class)
    fun `likeMeal should throw if no current meal`() {
        useCase.likeMeal()
    }

    @Test
    fun `dislikeMeal should return a new meal`() {
        val meal1 = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        val meal2 = Meal("2", "dirty  broccoli", Nutrition(4.0, 11.0, 10.0))
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        val first = useCase.getKetoMeal()
        val next = useCase.dislikeMeal()

        assertThat(next).isNotEqualTo(first)
    }
}

class KetoDietMealHelperUITest {
    private lateinit var ioHandler: IOHandler
    private lateinit var useCase: GetKetoDietMealUseCase
    private lateinit var ui: KetoDietMealHelperUI

    @Before
    fun setup() {
        ioHandler = mockk(relaxed = true)
        useCase = mockk(relaxed = true)
        ui = KetoDietMealHelperUI(useCase, ioHandler)
    }

    @Test
    fun `UI should display meal name only initially`() {
        val meal = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        every { useCase.getKetoMeal() } returns meal
        every { ioHandler.readLine() } returns "1"
        every { useCase.likeMeal() } returns meal

        ui.run()

        verify { ioHandler.printLine(match { it.contains("Suggested meal: good morning   muffins") }) }
    }

    @Test
    fun `UI should handle like option correctly`() {
        val meal = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        every { useCase.getKetoMeal() } returns meal
        every { ioHandler.readLine() } returnsMany listOf("1", "3")
        every { useCase.likeMeal() } returns meal

        ui.run()

        verify { ioHandler.printLine(match { it.contains("Description") }) }
    }

    @Test
    fun `UI should handle dislike option correctly`() {
        val meal1 = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        val meal2 = Meal("2", "dirty  broccoli", Nutrition(4.0, 11.0, 10.0))
        every { useCase.getKetoMeal() } returnsMany listOf(meal1, meal2)
        every { ioHandler.readLine() } returnsMany listOf("2", "3")

        ui.run()

        verify { ioHandler.printLine(match { it.contains("Suggested meal: dirty  broccoli") }) }
    }

    @Test
    fun `UI should exit on option 3`() {
        val meal = Meal("1", "good morning   muffins", Nutrition(9.0, 11.0, 5.0))
        every { useCase.getKetoMeal() } returns meal
        every { ioHandler.readLine() } returns "3"

        ui.run()

        verify(exactly = 1) { ioHandler.printLine(match { it.contains("Goodbye!") }) }
    }
}
