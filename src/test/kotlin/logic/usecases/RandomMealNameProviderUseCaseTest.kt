import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
import org.example.model.Meal
import org.example.model.Nutrition
import kotlin.test.Test
import kotlin.test.assertFailsWith

class RandomMealNameProviderUseCaseTest {
 private val mockMealRepository: MealRepository = mockk()
 private val useCase = RandomMealNameProviderUseCase(mockMealRepository)
 @Test
 fun `when meals exist, returns random meal name from repository`() {
  val testMeals = listOf(
   createTestMeal(name = "Burger"),
   createTestMeal(name = "Pizza"),
   createTestMeal(name = "Pasta")
  )
  every { mockMealRepository.getAllMeals() } returns testMeals
  val result = useCase.getRandomMeal().name
  assertTrue(result in listOf("Burger", "Pizza", "Pasta"))
 }

 @Test
 fun `when the name of the meal is empty, throws NoFoodFoundException`() {
  val testMeals = listOf(createTestMeal(name = ""))
  every { mockMealRepository.getAllMeals() } returns testMeals
  assertFailsWith<Exceptions.NoMealsFoundException> {
   useCase.getRandomMeal()
  }
 }

 @Test
 fun `when the name of the meal is space, throws NoFoodFoundException`() {
  val testMeals = listOf(createTestMeal(name = "   "))
  every { mockMealRepository.getAllMeals() } returns testMeals
  assertFailsWith<Exceptions.NoMealsFoundException> {
   useCase.getRandomMeal()
  }
 }
 @Test
 fun `when all meals are invalid, throws NoFoodFoundException`() {
  val testMeals = listOf(
   createTestMeal(name = ""),
   createTestMeal(name = "   "),
   createTestMeal(name = null)
  )
  every { mockMealRepository.getAllMeals() } returns testMeals
  assertFailsWith<Exceptions.NoMealsFoundException> {
   useCase.getRandomMeal()
  }
 }
 @Test
 fun `when valid meals exist, returns meal with name`() {
  val expectedName = "Chicken Tikka Masala"
  val testMeals = listOf(createTestMeal(name = expectedName))
  every { mockMealRepository.getAllMeals() } returns testMeals
  val result = useCase.getRandomMeal().name
  assertEquals(expectedName, result)
 }

 private fun createTestMeal(
  name: String?,
  id: Int = 1,
  minutes: Int = 30,
  contributorId: Int = 1,
  submitted: String = "2023-01-01",
  tags: List<String> = emptyList(),
  nutrition: Nutrition = Nutrition(
   calories = null,
   totalFat = null,
   sugar = null,
   sodium = null,
   protein = null,
   saturatedFat = null,
   carbohydrates = null
  ),
  nSteps: Int = 5,
  steps: List<String> = emptyList(),
  description: String = "Delicious meal",
  ingredients: List<String> = emptyList(),
  nIngredients: Int = 5
 ): Meal {
  return Meal(
   name = name,
   id = id,
   minutes = minutes,
   contributorId = contributorId,
   submitted = submitted,
   tags = tags,
   nutrition = nutrition,
   nSteps = nSteps,
   steps = steps,
   description = description,
   ingredients = ingredients,
   nIngredients = nIngredients
  )
 }
}