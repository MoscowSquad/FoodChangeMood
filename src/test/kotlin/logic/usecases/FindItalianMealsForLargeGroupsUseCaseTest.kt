package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.FindItalianMealsForLargeGroupsUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith


class FindItalianMealsForLargeGroupsUseCaseTest {

    private lateinit var mealRepository: MealRepository
    private lateinit var useCase: FindItalianMealsForLargeGroupsUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk(relaxed = true)
        useCase = FindItalianMealsForLargeGroupsUseCase(mealRepository)
    }


    @Test
    fun `should return matching Italian  meals for large groups when called`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,
                tags = listOf("ITALIAN", "for-large-groups", "DINNER-PARTY"),
                description = "A classic ITALIAN dessert."

            ),
            createMealHelper(
            name = "Tiramisu",
            id = 2,
            tags = listOf("dessert", "for-large-groups"),
                description = "A classic ITALIAN dessert."
            ),
        )
        val result = useCase()
        assertThat(result.map { it.name to it.id }).containsExactly(
            "Lasagna Bolognese" to 1,
            "Tiramisu" to 2
        )
    }

    @Test
    fun `should ignore italian when meals not suitable for groups`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Pizza",
                id = 3,
                tags = listOf("main-dish"),
                description = "Delicious italian food"
            )
        )

        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }

    @Test
    fun `should ignore non-italian when meals suitable for groups`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Sushi",
                id = 4,
                tags = listOf("for-large-groups"),
                description = "Japanese classic"
            )
        )
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }

    @Test
    fun `should ignore meals when that are neither italian nor for groups`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Burger",
                id = 5,
                tags = listOf("fast-food"),
                description = "Just a regular burger"
            )
        )
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }


    @Test
    fun `should throw exception when no matching meals found`() {
        every { mealRepository.getAllMeals() } returns emptyList()

        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }


    @Test
    fun `should handle null tags and description correctly`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Spaghetti",
                id = 6,
                tags = null,
                description = null
            )
        )
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }


    @Test
    fun `should handle null description with valid tags for Italian meal`() {
        every { mealRepository.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Risotto",
                id = 8,
                tags = listOf("ITALIAN", "for-large-groups"),
                description = null
            )
        )

        val result = useCase()
        assertThat(result.map { it.name to it.id }).containsExactly(
            "Risotto" to 8
        )
    }
}