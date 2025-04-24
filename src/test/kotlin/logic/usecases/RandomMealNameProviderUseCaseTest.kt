
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.usecases.createMeal
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
import kotlin.test.Test
import kotlin.test.assertFailsWith


class RandomMealNameProviderUseCaseTest {
    private val mockMealRepository: MealRepository = mockk()
    private val useCase = RandomMealNameProviderUseCase(mockMealRepository)
    @Test
    fun `when meals exist, returns random meal name from repository`() {
        val testMeals = listOf(
            createMeal(name = "Burger"),
            createMeal(name = "Pizza"),
            createMeal(name = "Pasta")
        )
        every { mockMealRepository.getAllMeals() } returns testMeals
        val result = useCase.getRandomMeal().name
        assertTrue(result in listOf("Burger", "Pizza", "Pasta"))
    }

    @Test
    fun `when the name of the meal is empty, throws NoFoodFoundException`() {
        val testMeals = listOf(createMeal(name = ""))
        every { mockMealRepository.getAllMeals() } returns testMeals
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.getRandomMeal()
        }
    }

    @Test
    fun `when the name of the meal is space, throws NoFoodFoundException`() {
        val testMeals = listOf(createMeal(name = "   "))
        every { mockMealRepository.getAllMeals() } returns testMeals
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.getRandomMeal()
        }
    }
    @Test
    fun `when all meals are invalid, throws NoFoodFoundException`() {
        val testMeals = listOf(
            createMeal(name = ""),
            createMeal(name = "   "),
            createMeal(name = null)
        )
        every { mockMealRepository.getAllMeals() } returns testMeals
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.getRandomMeal()
        }
    }
    @Test
    fun `when valid meals exist, returns meal with name`() {
        val expectedName = "Chicken Tikka Masala"
        val testMeals = listOf(createMeal(name = expectedName))
        every { mockMealRepository.getAllMeals() } returns testMeals
        val result = useCase.getRandomMeal().name
        assertEquals(expectedName, result)
    }

    
}