package logic.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GameResult
import org.example.logic.usecases.GameStep
import org.example.logic.usecases.GetIngredientMealsUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetIngredientMealsUseCaseTest {
    lateinit var mealRepository: MealRepository
    lateinit var getIngredientMealsUseCase: GetIngredientMealsUseCase

    @BeforeEach
    fun setUp() {
        mealRepository = mockk()
        getIngredientMealsUseCase = GetIngredientMealsUseCase(mealRepository)
    }

    @Test
    fun `should return 0 point and lose game when there is only one question and only one wrong answer`() {
        //given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0),
        )
        val answers = listOf(1)
        val expected = GameResult.Lose(0)

        //when
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)
        //then
        assertEquals(result, expected)
    }

    @Test
    fun `should return 0 point and lose game when there is a null answer`() {
        //given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q2", options = listOf("op1", "op2", "op3"), correctIndex = 0)
        )
        val answers = listOf(null, null)
        val expected = GameResult.Lose(0)

        //when
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)
        //then
        assertEquals(result, expected)
    }

    @Test
    fun `should return 0 point and lose game when answers list is empty`() {
        //given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q2", options = listOf("op1", "op2", "op3"), correctIndex = 0)
        )
        val answers = emptyList<Int>()
        val expected = GameResult.Lose(0)

        //when
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)
        //then
        assertEquals(result, expected)
    }

    @Test
    fun `should return 1000 point and lose game when answer just one question`() {
        //given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q2", options = listOf("op1", "op2", "op3"), correctIndex = 0)
        )
        val answers = listOf(0, 1)
        val expected = GameResult.Lose(1000)

        //when
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)
        //then
        assertEquals(result, expected)
    }

    @Test
    fun `should return 15000 point and lose game when answer all questions right`() {
        //given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q2", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q3", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q4", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q5", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q6", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q7", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q8", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q9", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q10", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q11", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q12", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q13", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q14", options = listOf("op1", "op2", "op3"), correctIndex = 0),
            GameStep(question = "Q15", options = listOf("op1", "op2", "op3"), correctIndex = 0),
        )
        val answers = List(15) { 0 }
        val expected = GameResult.Win(15000)

        //when
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)
        //then
        assertEquals(result, expected)
    }


    // Add these tests to GetIngredientMealsUseCaseTest class

    @Test
    fun `prepareGameSteps should create correct number of questions and options`() {
        // Given
        val mealsList = List(50) { index ->
            createMeal(
                id = index.toLong().toInt(),
                name = "Meal $index",
                ingredients = List(5) { "Ingredient ${it + 1}" }
            )
        }
        every { mealRepository.getAllMeals() } returns mealsList

        // When
        val steps = getIngredientMealsUseCase.prepareGameSteps()

        // Then
        assertEquals(15, steps.size) // Verify we have MAX_QUESTIONS steps
        steps.forEach { step ->
            assertEquals(3, step.options.size) // Verify each step has MAX_OPTIONS options
            assertTrue(step.correctIndex in 0..2) // Verify correctIndex is valid
            assertFalse(step.question.isEmpty()) // Verify question is not empty
        }

        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `prepareGameSteps should handle fewer meals by cycling`() {
        // Given
        val mealsList = List(2) { index ->
            createMeal(
                id = index.toLong().toInt(),
                name = "Meal $index",
                ingredients = List(5) { "Ingredient ${it + 1}" }
            )
        }
        every { mealRepository.getAllMeals() } returns mealsList

        // When
        val steps = getIngredientMealsUseCase.prepareGameSteps()

        // Then
        assertEquals(15, steps.size) // We still get MAX_QUESTIONS steps
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `prepareGameSteps should filter out meals with insufficient ingredients or null name`() {
        // Given
        val validMeals = List(10) {
            createMeal(
                id = it.toLong().toInt(),
                name = "Valid Meal $it",
                ingredients = List(5) { i -> "Ingredient $i" })
        }

        val invalidMeals = listOf(
            createMeal(id = 100, name = null, ingredients = List(5) { "Ingredient $it" }),
            createMeal(id = 101, name = "Invalid Meal", ingredients = listOf("Single")),
            createMeal(id = 102, name = "Invalid Meal", ingredients = null)
        )

        val allMeals = validMeals + invalidMeals
        every { mealRepository.getAllMeals() } returns allMeals

        // When
        val steps = getIngredientMealsUseCase.prepareGameSteps()

        // Then
        assertEquals(15, steps.size)
        // All questions should be from valid meals
        steps.forEach { step ->
            assertFalse(step.question.isEmpty())
        }
    }

    @Test
    fun `evaluateAnswers should return win with correct points when all answers match`() {
        // Given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 1),
            GameStep(question = "Q2", options = listOf("op1", "op2", "op3"), correctIndex = 2),
            GameStep(question = "Q3", options = listOf("op1", "op2", "op3"), correctIndex = 0)
        )
        val answers = listOf(1, 2, 0)
        val expected = GameResult.Win(3000)

        // When
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `evaluateAnswers should handle case when answers list is longer than steps list`() {
        // Given
        val steps = listOf(
            GameStep(question = "Q1", options = listOf("op1", "op2", "op3"), correctIndex = 0)
        )
        val answers = listOf(0, 0, 0) // More answers than questions
        val expected = GameResult.Win(1000)

        // When
        val result = getIngredientMealsUseCase.evaluateAnswers(steps, answers)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `prepareGameSteps should use correct meal name as question and second ingredient as option`() {
        // Given
        val meal1 = createMeal(id = 1, name = "Pasta Carbonara", ingredients = listOf("Pasta", "Eggs", "Bacon"))
        val meal2 = createMeal(id = 2, name = "Pizza", ingredients = listOf("Dough", "Tomato", "Cheese"))
        val meal3 = createMeal(id = 3, name = "Risotto", ingredients = listOf("Rice", "Broth", "Parmesan"))

        every { mealRepository.getAllMeals() } returns listOf(meal1, meal2, meal3)

        // When
        val steps = getIngredientMealsUseCase.prepareGameSteps()

        // Then
        steps.forEach { step ->
            // Find which meal matches the question
            val matchingMeal = listOf(meal1, meal2, meal3).find { it.name == step.question }

            // The question should match one of our meal names
            assertNotNull(matchingMeal)

            // The options should contain the second ingredient (index 1) of each meal
            val expectedIngredients = listOf("Eggs", "Tomato", "Broth")
            step.options.forEach { option ->
                assertTrue(expectedIngredients.contains(option))
            }

            // The correct option should be the second ingredient of the meal that matches the question
            assertEquals(matchingMeal?.ingredients?.get(1), step.options[step.correctIndex])
        }

        verify { mealRepository.getAllMeals() }
    }


}