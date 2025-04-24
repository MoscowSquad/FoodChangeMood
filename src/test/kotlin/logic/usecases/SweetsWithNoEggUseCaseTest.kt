package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.SweetsWithNoEggUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SweetsWithNoEggUseCaseTest {

    private lateinit var sweetsWithNoEggUseCase: SweetsWithNoEggUseCase
    private lateinit var mealRepository: MealRepository

    @BeforeEach
    fun setUp() {
        mealRepository = mockk(relaxed = true)
        sweetsWithNoEggUseCase = SweetsWithNoEggUseCase(mealRepository)
    }

    @Test
    fun `should throw an exception when no sweet meals without eggs found`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "easy one-pot dinner.",
                tags = listOf("occasion", "north-american", "main-dish"),
                ingredients = listOf("sugar")
            ),
            createMeal(
                id = 2,
                description = "hard one-pot dinner",
                tags = listOf("american", "side-dish", "dinner"),
                ingredients = listOf("sugar")
            ),
            createMeal(
                id = 3,
                description = "very hot sausage",
                tags = listOf("african", "side-dish", "luxury"),
                ingredients = listOf("sugar")
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should throw an exception when no there is no meals`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should throw exception when meal id is null`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = null,
                description = "easy one-pot dinner.",
                tags = listOf("occasion", "north-american", "main-dish"),
                ingredients = listOf("sugar")
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should throw exception when all matching meals are shown`() {
        // Given
        val meal = createMeal(
            id = 1,
            description = "sweet",
            tags = listOf("occasion", "north-american", "main-dish"),
            ingredients = listOf("sugar")
        )
        every { mealRepository.getAllMeals() } returns listOf(
            meal
        )

        // When & Then
        assertEquals(meal, sweetsWithNoEggUseCase())
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should throw exception when tags or description with null value`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = 1
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should throw exception when there is no matching meals `() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                description = "meal",
                tags = listOf("occasion", "north-american", "main-dish"),
                ingredients = listOf("sugar")
            ),
            createMeal(
                id = 2,
                description = "cat",
                tags = listOf("occasion", "north-american", "main-dish"),
                ingredients = listOf("sugar")
            ),
            createMeal(
                id = 3,
                description = "meat",
                tags = listOf("occasion", "north-american", "main-dish"),
                ingredients = listOf("sugar")
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should return a valid sweet meal with no egg`() {
        // Given
        val expectedMeal = createMeal(
            id = 10,
            description = "This is a sweet dessert",
            tags = listOf("dessert"),
            ingredients = listOf("sugar", "milk")
        )
        every { mealRepository.getAllMeals() } returns listOf(expectedMeal)

        // When
        val result = sweetsWithNoEggUseCase()

        // Then
        assertEquals(expectedMeal, result)
    }

    @Test
    fun `should ignore meals with egg in ingredients`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = 11,
                description = "tasty sweet dish",
                tags = listOf("sweet"),
                ingredients = listOf("egg", "sugar")
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should ignore meals with egg in description`() {
        // Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(
                id = 12,
                description = "sweet and has egg inside",
                tags = listOf("dessert"),
                ingredients = listOf("sugar", "flour")
            )
        )

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            sweetsWithNoEggUseCase()
        }
    }

    @Test
    fun `should match case-insensitive sweet and egg in tags and description`() {
        // Given
        val validMeal = createMeal(
            id = 13,
            description = "SwEeT chocolate cake",
            tags = listOf("DeSsErT"),
            ingredients = listOf("chocolate", "sugar")
        )
        val eggMeal = createMeal(
            id = 14,
            description = "something SWEET but contains EGG",
            tags = listOf("sweet"),
            ingredients = listOf("EGG")
        )

        every { mealRepository.getAllMeals() } returns listOf(eggMeal, validMeal)

        // When
        val result = sweetsWithNoEggUseCase()

        // Then
        assertEquals(validMeal, result)
    }

    @Test
    fun `should skip previously shown meal and return a new one if available`() {
        // Given
        val firstMeal = createMeal(
            id = 15,
            description = "sweet treat",
            tags = listOf("dessert"),
            ingredients = listOf("sugar")
        )
        val secondMeal = createMeal(
            id = 16,
            description = "sweet snack",
            tags = listOf("snack"),
            ingredients = listOf("sugar")
        )

        every { mealRepository.getAllMeals() } returns listOf(firstMeal, secondMeal)

        // When
        // First call: return and mark firstMeal as shown
        val result1 = sweetsWithNoEggUseCase()
        // Second call: should return secondMeal
        val result2 = sweetsWithNoEggUseCase()

        assert(result1 != result2)
        assert(listOf(firstMeal, secondMeal).containsAll(listOf(result1, result2)))
    }
}