package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
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

    // getRandomMeal() tests
    @Test
    fun `getRandomMeal should return valid meal when repository has meals`() {
        // Given
        val testMeals = listOf(
            createMeal("Pizza", 30),
            createMeal("Pasta", 15)
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
            createMeal(null, 30),
            createMeal("Burger", 20)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getRandomMeal()

        // Then
        assertEquals("Burger", result.name)
    }

    @Test
    fun `getRandomMeal should filter out meals with blank names`() {
        // Given
        val testMeals = listOf(
            createMeal("", 30),
            createMeal("  ", 20),
            createMeal("Burger", 15)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getRandomMeal()

        // Then
        assertEquals("Burger", result.name)
    }


    @Test
    fun `isSuggestRight should return false when minutes don't match`() {
        // Given
        val testMeal = createMeal("Pizza", 30)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When
        val result = useCase.isSuggestRight(45)

        // Then
        assertFalse(result, "Expected false when minutes don't match")
    }

    @Test
    fun `isSuggestRight should return true when minutes match`() {
        // Given
        val testMeal = createMeal("Pizza", 30)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When
        val result = useCase.isSuggestRight(30)

        // Then
        assertFalse(result, "Expected true when minutes  match")
    }

    @Test
    fun `isSuggestRight should throw when meal is null`() {
        // When & Then (no meal set up)
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.isSuggestRight(15)
        }
    }

    @Test
    fun `isSuggestRight should throw when suggestion is null`() {
        // Given
        val testMeal = createMeal("Fish", 15)
        every { mockMealRepository.getAllMeals() } returns listOf(testMeal)
        useCase.getRandomMeal()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.isSuggestRight(null)
        }
    }

}