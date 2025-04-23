package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.presentation.KetoDietMealHelperUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class KetoDietMealHelperUITest {

    private lateinit var getKetoDietMealUseCase: GetKetoDietMealUseCase
    private lateinit var ui: KetoDietMealHelperUI

    @BeforeEach
    fun setUp() {
        getKetoDietMealUseCase = mockk()
        ui = KetoDietMealHelperUI(getKetoDietMealUseCase)
    }

    @Test
    fun `should suggest a meal and allow user to like it`() {
        // Given:
        val meal = (
                createMeal("good morning muffins", 215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)
                )
        every { getKetoDietMealUseCase.getKetoMeal() } returns meal

        // When:

        // Then:
        //meal.display()
    }

    @Test
    fun `should suggest a meal and allow user to dislike it and get a new suggestion`() {
        // Given:
        val meal1 = (
                createMeal("good morning muffins", 215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)
                )
        val meal2 = (
                createMeal("dirty  broccoli", 137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0)
                )

        every { getKetoDietMealUseCase.getKetoMeal() } returns meal1 andThen meal2

        // When:

        // Then:
        //meal2.display()
    }

    @Test
    fun `should handle MealNotFoundException gracefully when no meals are available`() {
        // Given:
        every { getKetoDietMealUseCase.getKetoMeal() } throws Exceptions.MealNotFoundException("No more keto-friendly meals available.")

        // When:

        // Then:
        assertFailsWith<Exceptions.MealNotFoundException> { ui.invoke() }
    }

    @Test
    fun `should handle invalid user option gracefully`() {
        // Given:
        val meal = (
                createMeal("good morning muffins", 215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)
                )
        every { getKetoDietMealUseCase.getKetoMeal() } returns meal

        // When:

        // Then:
    }
}