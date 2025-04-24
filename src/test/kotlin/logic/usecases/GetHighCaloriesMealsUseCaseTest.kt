package logic.usecases

import com.google.common.truth.Truth
import fake.HighCaloriesMockDataRepository
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetHighCaloriesMealsUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetHighCaloriesMealsUseCaseTest {
    lateinit var repository: MealRepository
    lateinit var getHighCaloriesMealsUseCase: GetHighCaloriesMealsUseCase

    @BeforeEach
    fun setUp() {
        repository = HighCaloriesMockDataRepository()
        getHighCaloriesMealsUseCase = GetHighCaloriesMealsUseCase(repository)
    }

    @Test
    fun `should return high calorie meal when there is high calories data`() {
        // Given
        val allMeals = repository.getAllMeals()

        // When
        val result = getHighCaloriesMealsUseCase.nextMeal()

        // Then
        Truth.assertThat(result.id).isEqualTo(93249)
    }

    @Test
    fun `should return a high-calorie meal when requested multiple times`() {
        for (i in 0 until 4) {
            // When
            val result = getHighCaloriesMealsUseCase.nextMeal()

            // Then
            when (i) {
                0 -> Truth.assertThat(result.id).isEqualTo(93249)
                1 -> Truth.assertThat(result.id).isEqualTo(44895)
                2 -> Truth.assertThat(result.id).isEqualTo(62368)
                3 -> Truth.assertThat(result.id).isEqualTo(77380)
            }
        }
    }

    @Test
    fun `should throw NoMealsFoundException when all high-calorie meals have already been retrieved`() {
        for (i in 0 until 4) {
            getHighCaloriesMealsUseCase.nextMeal()
        }

        // When, Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getHighCaloriesMealsUseCase.nextMeal()
        }
    }
}