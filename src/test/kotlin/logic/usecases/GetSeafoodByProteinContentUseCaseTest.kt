package logic.usecases

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetSeafoodByProteinContentUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetSeafoodByProteinContentUseCaseTest {

    private val mockMealRepository: MealRepository = mockk()
    private lateinit var useCase: GetSeafoodByProteinContentUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetSeafoodByProteinContentUseCase(mockMealRepository)
    }

    @Test
    fun `getSeafoodMealsByProteinContent should return meals that contain seafood in tags`() {
        // Given
        val testMeals = listOf(
            createMeal(id = 8, name = "Medium", tags = listOf("seafood"), nutrition = createNutrition(protein = 25.0)),
            createMeal(id = 4, name = "NonSeafood", nutrition = createNutrition(protein = 25.0)),
            createMeal(id = 355, name = "Low", tags = listOf("seafood"), nutrition = createNutrition(protein = 25.0)),
            createMeal(id = 54, name = "NonSeafood without nutrition"),
            createMeal(id = 25, name = "High", tags = listOf("seafood"), nutrition = createNutrition(protein = 25.0)),
            createMeal(id = 6, name = "NonSeafood without protein", nutrition = createNutrition(protein = null)),
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getSeafoodMealsByProteinContent()
        val ids = result.map { it.id }

        // Then
        Truth.assertThat(ids).isEqualTo(listOf(8, 355, 25))
    }

    @Test
    fun `getSeafoodMealsByProteinContent should throw NoMealsFoundException if no meals have seafood tag`() {
        // Given
        val testMeals = listOf(
            createMeal(id = 1, name = "Medium", nutrition = createNutrition(protein = 25.0)),
            createMeal(id = 2, name = "High"),
            createMeal(id = 3, name = "Low", nutrition = createNutrition(protein = null)),
            createMeal(id = 4, name = "NonSeafood")
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getSeafoodMealsByProteinContent()
        }
    }

    @Test
    fun `getSeafoodMealsByProteinContent should return meals that contain seafood in description`() {
        // Given
        val testMeals = listOf(
            createMeal(
                id = 8,
                name = "Medium",
                description = "seafood with medium protein",
                nutrition = createNutrition(protein = 25.0)
            ),
            createMeal(id = 4, name = "NonSeafood", nutrition = createNutrition(protein = 25.0)),
            createMeal(
                id = 355,
                name = "Low",
                description = "seafood with low protein",
                nutrition = createNutrition(protein = 25.0)
            ),
            createMeal(id = 54, name = "NonSeafood without nutrition"),
            createMeal(
                id = 25,
                name = "High",
                description = "seafood with high protein",
                nutrition = createNutrition(protein = 25.0)
            ),
            createMeal(id = 6, name = "NonSeafood without protein", nutrition = createNutrition(protein = null)),
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getSeafoodMealsByProteinContent()
        val ids = result.map { it.id }

        // Then
        Truth.assertThat(ids).isEqualTo(listOf(8, 355, 25))
    }


    @Test
    fun `getSeafoodMealsByProteinContent should throw if no meals have seafood in description`() {
        // Given
        val testMeals = listOf(
            createMeal(id = 1, name = "Medium", description = "chicken masala"),
            createMeal(id = 2, name = "High", description = "seizure salad"),
            createMeal(id = 3, name = "Low", nutrition = createNutrition(protein = null)),
            createMeal(id = 4, name = "NonSeafood", description = "small pizza")
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getSeafoodMealsByProteinContent()
        }
    }

    @Test
    fun `getSeafoodMealsByProteinContent should ignore meals without protein data`() {
        // Given
        val testMeals = listOf(
            createMeal(id = 1, name = "Valid", tags = listOf("seafood"), nutrition = createNutrition(protein = 25.0)),
            createMeal(
                id = 2,
                name = "NoProtein",
                tags = listOf("seafood"),
                nutrition = createNutrition(protein = null)
            ),
            createMeal(id = 3, name = "NoNutrition", tags = listOf("seafood"))
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getSeafoodMealsByProteinContent()
        val ids = result.map { it.id }

        // Then
        Truth.assertThat(ids).isEqualTo(listOf(1))
    }

    @Test
    fun `getSeafoodMealsByProteinContent should throw when seafood meals exist but all have null protein`() {
        // Given
        val testMeals = listOf(
            createMeal(
                id = 1,
                name = "Seafood1",
                tags = listOf("seafood"),
                nutrition = createNutrition(protein = null)
            ),
            createMeal(id = 2, name = "Seafood2", tags = listOf("seafood")),
            createMeal(
                id = 3,
                name = "Seafood3",
                description = "tasty seafood",
                nutrition = createNutrition(protein = null)
            )
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getSeafoodMealsByProteinContent()
        }
    }

    @Test
    fun `getSeafoodMealsByProteinContent should throw when repository returns empty list`() {
        // Given
        every { mockMealRepository.getAllMeals() } returns emptyList()

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getSeafoodMealsByProteinContent()
        }
    }


    @Test
    fun `getSeafoodMealsByProteinContent should handle meals with null tags and description`() {
        // Given
        val testMeals = listOf(
            createMeal(id = 1, name = "Valid", tags = listOf("seafood"), nutrition = createNutrition(protein = 25.0)),
            createMeal(
                id = 2,
                name = "Null Fields",
                tags = null,
                description = null,
                nutrition = createNutrition(protein = 30.0)
            )
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getSeafoodMealsByProteinContent()

        // Then
        Truth.assertThat(result.map { it.id }).containsExactly(1)
    }

    @Test
    fun `getSeafoodMealsByProteinContent should include meal when seafood is in both tags and description`() {
        // Given
        val testMeals = listOf(
            createMeal(
                id = 1, name = "Dual Seafood",
                tags = listOf("seafood"),
                description = "A seafood dish",
                nutrition = createNutrition(protein = 25.0)
            )
        )
        every { mockMealRepository.getAllMeals() } returns testMeals

        // When
        val result = useCase.getSeafoodMealsByProteinContent()

        // Then
        Truth.assertThat(result.map { it.id }).containsExactly(1)
    }

}
