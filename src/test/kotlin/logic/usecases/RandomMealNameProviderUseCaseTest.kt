package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RandomMealNameProviderUseCaseTest {
    private val mockMealRepository: MealRepository = mockk()
    private lateinit var useCase: RandomMealNameProviderUseCase

    @BeforeEach
    fun setUp() {
        useCase = RandomMealNameProviderUseCase(mockMealRepository)
    }
    @Test
    fun `getRandomMeal should filter out meals with blank names`() {
        // Given
        val testMeals = listOf(
            createTestMeal("", 30),
            createTestMeal("  ", 20),
            createTestMeal("Burger", 15)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getRandomMeal()

        // Then
        assertEquals("Burger", result.name)
    }
    // Test for getRandomMeal()
    @Test
    fun `getRandomMeal should return valid meal when repository has meals`() {
        // Given
        val testMeals = listOf(
            createTestMeal("Pizza", 30),
            createTestMeal("Pasta", 15)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getRandomMeal()

        // Then
        assertTrue(result.name == "Pizza" || result.name == "Pasta")
    }

    @Test
    fun `getRandomMeal should throw when repository is empty`() {
        // Given
        every { mockMealRepository.getAllMeals() } returns emptyList()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getRandomMeal()
        }
    }

    @Test
    fun `getRandomMeal should filter out meals with null names`() {
        // Given
        val testMeals = listOf(
            createTestMeal(null, 30),
            createTestMeal("Burger", 20)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getRandomMeal()

        // Then
        assertEquals("Burger", result.name)
    }

    // Tests for isSuggestRight()
    @Test
    fun `isSuggestRight should return true when minutes match`() {
        // Given
        val testMeal = createTestMeal("Salad", 10)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When
        val result = useCase.isSuggestRight(10)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isSuggestRight should return false when minutes dont match`() {
        // Given
        val testMeal = createTestMeal("Steak", 25)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When
        val result = useCase.isSuggestRight(30)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isSuggestRight should throw when no meal is selected`() {
        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.isSuggestRight(10)
        }
    }

    @Test
    fun `isSuggestRight should throw when suggestion is null`() {
        // Given
        val testMeal = createTestMeal("Fish", 15)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.isSuggestRight(null)
        }
    }

    // Helper function
    private fun createTestMeal(
        name: String?,
        minutes: Int,
        id: Int = 1,
        contributorId: Int = 1,
        submitted: String = "2023-01-01",
        tags: List<String> = emptyList(),
        nutrition: Nutrition = Nutrition(
            calories = null, totalFat = null, sugar = null,
            sodium = null, protein = null, saturatedFat = null,
            carbohydrates = null
        ),
        nSteps: Int = 5,
        steps: List<String> = emptyList(),
        description: String = "Delicious meal",
        ingredients: List<String> = emptyList(),
        nIngredients: Int = 5
    ) = Meal(
        name = name, id = id, minutes = minutes,
        contributorId = contributorId, submitted = submitted,
        tags = tags, nutrition = nutrition, nSteps = nSteps,
        steps = steps, description = description,
        ingredients = ingredients, nIngredients = nIngredients
    )
}