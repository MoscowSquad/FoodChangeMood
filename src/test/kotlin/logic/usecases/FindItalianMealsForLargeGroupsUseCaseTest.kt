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
    fun `should return Italian meals for large groups when called`() {
        val matchingMeal1 = createMealHelper(
            name = "Lasagna Bolognese",
            id = 1,
            tags = listOf("ITALIAN", "main-dish", "DINNER-PARTY")

        )

        val matchingMeal2 = createMealHelper(
            name = "Tiramisu",
            id = 2,
            tags = listOf("dessert", "for-large-groups"),
            description = "A classic ITALIAN dessert."
        )

        val nonMatchingMeal1 = createMealHelper(
            name = "French Cassoulet",
            id = 3,
            tags = listOf("french", "main-dish"),
        )

        val nonMatchingMeal2 = createMealHelper(
            name = "Spaghetti Aglio e Olio",
            id = 4,
            tags = listOf("italian", "quick"),
        )

        val nonMatchingMeal3 = createMealHelper(
            name = "Pasta Salad",
            id = 5,
            tags = null,
            description = null,
        )

        every { mealRepository.getAllMeals() } returns listOf(
            matchingMeal1,
            matchingMeal2,
            nonMatchingMeal1,
            nonMatchingMeal2,
            nonMatchingMeal3
        )

        val result = useCase()

        assertThat(result[0].tags).contains("ITALIAN")
    }

    @Test
    fun `should throw exception when no matching meals found`() {
        every { mealRepository.getAllMeals() } returns emptyList()

        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase()
        }
    }
}