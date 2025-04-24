package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetGymMealsUseCase
import org.example.model.Exceptions
import org.example.model.Nutrition
import org.example.model.NutritionRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GetGymMealsUseCaseTest {
    private lateinit var meals: MealRepository
    private lateinit var useCase: GetGymMealsUseCase

    @BeforeEach
    fun setUp() {
        meals = mockk(relaxed = true)
        useCase = GetGymMealsUseCase(meals)
    }

    @Test
    fun `should return list of meal when protein and calories are within tolerance`() {

        every { meals.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,
                nutrition = Nutrition(
                    calories = 101.0,
                    protein = 20.4,
                    totalFat = null, sugar = null, sodium = null, saturatedFat = null, carbohydrates = null
                )

            ),
        )

        val result = useCase.invoke(NutritionRequest(desiredCalories = 100.0, desiredProtein = 20.0))

        assertThat(result.map { it.name to it.id }).containsExactly(
            "Lasagna Bolognese" to 1,
        )

    }

    @Test
    fun `should throw exception when calories and protein are null`() {
        every { meals.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,
                nutrition = Nutrition(
                    calories = null,
                    protein = null,
                    totalFat = null, sugar = null, sodium = null, saturatedFat = null, carbohydrates = null
                )

            ),
        )

        assertFailsWith<Exceptions.NoMealsFoundException> {
            GetGymMealsUseCase(meals).invoke(NutritionRequest(desiredCalories = 100.0, desiredProtein = 50.0))
        }
    }

    @Test
    fun `should throw exception when protein is null but calories exists`() {
        every { meals.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,
                nutrition = Nutrition(
                    calories = 101.0,
                    protein = null,
                    totalFat = null,
                    sugar = null,
                    sodium = null,
                    saturatedFat = null,
                    carbohydrates = null
                )
            )
        )

        val request = NutritionRequest(desiredCalories = 100.0, desiredProtein = 20.0)
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.invoke(request)
        }
    }

    @Test
    fun `should throw exception when nutrition is null`() {
        every { meals.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,
                nutrition = null
            )
        )
        val request = NutritionRequest(desiredCalories = 0.0, desiredProtein = 0.0)
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.invoke(request)
        }
    }

    @Test
    fun `should throw exception when nutrition not found`() {
        every { meals.getAllMeals() } returns listOf(
            createMealHelper(
                name = "Lasagna Bolognese",
                id = 1,

                )
        )
        val request = NutritionRequest(desiredCalories = 0.0, desiredProtein = 0.0)
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.invoke(request)
        }
    }

    @Test
    fun `should throw exception when no meal found`() {
        every { meals.getAllMeals() } returns emptyList()

        assertFailsWith<Exceptions.NoMealsFoundException> {
            GetGymMealsUseCase(meals).invoke(NutritionRequest(desiredCalories = 0.0, desiredProtein = 0.0))
        }
    }

}