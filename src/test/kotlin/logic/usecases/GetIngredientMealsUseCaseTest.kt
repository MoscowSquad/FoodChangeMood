package logic.usecases

import io.mockk.mockk
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GameResult
import org.example.logic.usecases.GameStep
import org.example.logic.usecases.GetIngredientMealsUseCase
import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `should return 0 point and lose game when there is only one question and only one worng answer`() {
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


}