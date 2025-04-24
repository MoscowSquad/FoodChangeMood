package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetRandomMealsHavePotatoesUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetRandomMealsHavePotatoesUseCaseTest {

    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: GetRandomMealsHavePotatoesUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk(relaxed = true)
        useCase = GetRandomMealsHavePotatoesUseCase(mealRepository)
    }

    // Success Scenarios

    @Test
    fun `should return meals containing potatoes - normal case`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = listOf("Potato", "Salt")),
            createMeal(id = 2, ingredients = listOf("Tomato", "Potatoes"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When
        val result = useCase()

        // Then
        assertEquals(2, result.size)
        assertTrue(result.all { it.ingredients!!.any { ing -> ing.contains("potato", true) } })
    }

    @Test
    fun `should ignore meals with null ingredients`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = null),
            createMeal(id = 2, ingredients = listOf("Potato"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When
        val result = useCase()

        // Then
        assertEquals(1, result.size)
        assertEquals(2, result.first().id)
    }

    @Test
    fun `should handle mixed case words like PoTaTo`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = listOf("PoTaTo", "Cheese"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When
        val result = useCase()

        // Then
        assertEquals(1, result.size)
        assertEquals(1, result.first().id)
    }

    @Test
    fun `should limit results to maximum 10 meals`() {
        // Given
        val meals = (1..20).map {
            createMeal(id = it, ingredients = listOf("potato", "oil"))
        }
        every { mealRepository.getAllMeals() } returns meals

        // When
        val result = useCase()

        // Then
        assertTrue(result.size <= 10)
        assertTrue(result.all { it.ingredients!!.any { ing -> ing.contains("potato", true) } })
    }

    @Test
    fun `random meal selection should not always return same items`() {
        // Given
        val meals = (1..20).map { id -> createMeal(id = id, ingredients = listOf("potato")) }
        every { mealRepository.getAllMeals() } returns meals

        // When
        val results = (1..3).map { useCase().map { it.id }.toSet() }

        // Then
        val allEqual = results.distinct().size == 1
        assertFalse(allEqual, "Random meals should vary across calls")
    }

    // Failure Scenarios

    @Test
    fun `should throw exception when no meal contains potato`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = listOf("Tomato")),
            createMeal(id = 2, ingredients = listOf("Salt", "Pepper"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When & Then
        val exception = assertThrows<Exceptions.NoMealsFoundException> {
            useCase()
        }
        assertEquals("No meals found with potatoes.", exception.message)
    }

    @Test
    fun `should return empty when ingredients list is empty`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = emptyList()),
            createMeal(id = 2, ingredients = listOf("rice"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When & Then
        val exception = assertThrows<Exceptions.NoMealsFoundException> {
            useCase()
        }
        assertEquals("No meals found with potatoes.", exception.message)
    }

    @Test
    fun `should throw NoMealsFoundException when no meals have potatoes`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, ingredients = listOf("rice", "chicken")),
            createMeal(id = 2, ingredients = listOf("beef", "onion"))
        )
        every { mealRepository.getAllMeals() } returns meals

        // When & Then
        val exception = assertThrows<Exceptions.NoMealsFoundException> {
            useCase()
        }
        assertEquals("No meals found with potatoes.", exception.message)
    }
}
