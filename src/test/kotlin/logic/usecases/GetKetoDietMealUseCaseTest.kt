package logic.usecases

import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetKetoDietMealUseCaseTest {

    private lateinit var repository: MealRepository
    private lateinit var useCase: GetKetoDietMealUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetKetoDietMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal should return a keto meal when available`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        )

        // When:
        val result = useCase.getKetoMeal()

        // Then:
        assertEquals("good morning muffins", result.name)
    }

    @Test
    fun `getKetoMeal should throw exception when no keto meals are available`() {
        // Given:
        every { repository.getAllMeals() } returns emptyList()

        // When:etoMeal should throw exception when no keto meals are availab

        // Then:
        assertFailsWith<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() }
    }

    @Test
    fun `likeMeal should return current meal when a meal is suggested`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        )
        useCase.getKetoMeal()  // Suggest the meal

        // When:
        val likedMeal = useCase.likeMeal()

        // Then:
        assertEquals("good morning muffins", likedMeal.name)
    }

    @Test
    fun `likeMeal should throw exception when no meal is currently suggested`() {
        // When:

        // Then:
        assertFailsWith<Exceptions.NoMealsFoundException> { useCase.likeMeal() }
    }

    @Test
    fun `dislikeMeal should return a new meal when disliked`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)),
            createMeal(name = "dirty  broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        )

        // When:
        useCase.getKetoMeal()

        // And:
        val newMeal = useCase.dislikeMeal()

        // Then:
        assertEquals("dirty  broccoli", newMeal.name)
    }

    @Test
    fun `dislikeMeal should throw exception if no meal is suggested yet`() {
        // When:

        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.dislikeMeal() }
    }

    @Test
    fun `getKetoMeal should not repeat previously suggested meals`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)),
            //createMeal(name = "dirty  broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        )

        // When:
        useCase.getKetoMeal()

        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() }
    }

}