package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetMealsByDateUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetMealsByDateUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var getMealsByDateUseCase: GetMealsByDateUseCase

    @BeforeEach
    fun setup(){
      mealRepository = mockk(relaxed = true)
      getMealsByDateUseCase = GetMealsByDateUseCase(mealRepository)
    }

    @Test
    fun `should return one meal matching with date`()
    {
        val trueMeal = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient"
            ),
            description = "autumn is my favorite time of year to cook!",
            submitted = "20040602"

        )
        val falseMeal = createMeal(
        name = "a bit different  breakfast pizza",
        tags = listOf(
            "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
        ),
        description = "this recipe calls for the crust to be prebaked a bit before adding ingredients.",
        submitted = "20060310"
    )
        val anotherFalseMeal = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "mexican", "weeknight", "time-to-make", "course", "main-ingredient",
            ),
            description = "easy one-pot dinner.",
            submitted = "20060310"
        )

        every { mealRepository.getAllMeals() } returns listOf(trueMeal , falseMeal , anotherFalseMeal)


        val searchInput = "20040602"

        // When
        val result = getMealsByDateUseCase.getMealsByDate(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal)

    }
    @Test
    fun `should return more than one meal matching with date`()
    {
        val trueMeal = createMeal(
            name = "arriba   baked winter squash mexican style",
            tags = listOf(
                "60-minutes-or-less", "time-to-make", "course", "main-ingredient"
            ),
            description = "autumn is my favorite time of year to cook!",
            submitted = "20040602"
        )
        val falseMeal = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients.",
            submitted = "20060310"
        )
        val anotherTrueMeal = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "mexican", "weeknight", "time-to-make", "course", "main-ingredient",
            ),
            description = "easy one-pot dinner.",
            submitted = "20040602"
        )

        every { mealRepository.getAllMeals() } returns listOf(trueMeal , falseMeal , anotherTrueMeal)


        val searchInput = "20040602"

        // When
        val result = getMealsByDateUseCase.getMealsByDate(searchInput)

        // Then
        assertThat(result).containsExactly(trueMeal , anotherTrueMeal)

    }
    @Test
    fun `should throw exception when no meal found when return empty`()
    {
        // Given
        every { mealRepository.getAllMeals() } returns listOf()
        val searchInput = "20040602"

        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getMealsByDateUseCase.getMealsByDate(searchInput)
        }
    }
    @Test
    fun `should throw exception when no meal found`()
    {
        val falseMeal = createMeal(
            name = "a bit different  breakfast pizza",
            tags = listOf(
                "30-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine"
            ),
            description = "this recipe calls for the crust to be prebaked a bit before adding ingredients.",
            submitted = "20060310"
        )
        val anotherFalseMeal = createMeal(
            name = "chinese  chop suey",
            tags = listOf(
                "mexican", "weeknight", "time-to-make", "course", "main-ingredient",
            ),
            description = "easy one-pot dinner.",
            submitted = "20040602"
        )
        // Given
        every { mealRepository.getAllMeals() } returns listOf(falseMeal , anotherFalseMeal)
        val searchInput = "20040622"
        // When & Then
        assertThrows<Exceptions.NoMealsFoundException> {
            getMealsByDateUseCase.getMealsByDate(searchInput)
        }
    }

}