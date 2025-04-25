package logic.usecases

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetIraqiMealsUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.stream.Collectors


class GetIraqiMealsUseCaseTest {
    private lateinit var mealRepository: MealRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk(relaxed = true)
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealRepository)
    }

    @Test
    fun `should return iraqi meal when tags contain iraqi word `() {
        //given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(id = 0, tags = listOf("minutes-or-less", "time-to-make", "course", "cuisine")),
            createMeal(id = 1, tags = listOf("preparation", "georgian", "iraqi", "jewish-sephardi")),
            createMeal(id = 2, tags = listOf("palestinian", "saudi-arabian", "condiments-etc", "asian")),
            createMeal(id = 3, tags = listOf("middle-eastern", "herb-and-spice-mixes", "lebanese", "turkish")),
            createMeal(id = 4, tags = listOf("cooking-mixes", "Iraqi", "number-of-servings")),

            )

        //when
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()
        val ids: List<Int?> = iraqiMeals.map { it.id }
        //then
        Truth.assertThat(ids).isAnyOf(1, 4)
    }

    @Test
    fun `should throw NoMealsFoundException when tags not contain iraqi word `() {
        //given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(id = 0, tags = listOf("minutes-or-less", "time-to-make", "course", "cuisine")),
            createMeal(id = 1, tags = listOf("preparation", "georgian", "jewish-sephardi")),
            createMeal(id = 2, tags = listOf("palestinian", "saudi-arabian", "condiments-etc", "asian")),
            createMeal(id = 3, tags = listOf("middle-eastern", "herb-and-spice-mixes", "lebanese", "turkish")),
            createMeal(id = 4, tags = listOf("cooking-mixes", "number-of-servings")),

            )
        // when then
        org.junit.jupiter.api.assertThrows<Exceptions.NoMealsFoundException> { getIraqiMealsUseCase.getIraqiMeals() }
    }

    @Test
    fun `should return iraqi meal when description contain iraqi word `() {
        //given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(id = 0, description = "iraq restuarant has many types of foods like dulma and rice and soup"),
            createMeal(id = 1, description = "all arab country have food rice and soup as common eat "),
            createMeal(id = 2, description = "every person in arab country like food of his country"),
            createMeal(id = 3, description = "people in iraq love food that contain fat"),
            createMeal(id = 4, description = "every city in iraq has private food"),

            )

        //when
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()
        val ids: List<Int?> = iraqiMeals.map { it.id }
        //then
        Truth.assertThat(ids).isAnyOf(0, 3, 4)
    }

    @Test
    fun `should throw NoMealsFoundException when description not contain iraq word `() {
        //given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(id = 0, description = "all restuarant has many types of foods like dulma and rice and soup"),
            createMeal(id = 1, description = "all arab country have food rice and soup as common eat "),
            createMeal(id = 2, description = "every person in arab country like food of his country"),
            createMeal(id = 3, description = "people in egypt love food that contain fat"),
            createMeal(id = 4, description = "every city in yemen has private food"),

            )

        //when then
        org.junit.jupiter.api.assertThrows<Exceptions.NoMealsFoundException> { getIraqiMealsUseCase.getIraqiMeals() }
    }
}
