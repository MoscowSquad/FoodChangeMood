package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.example.logic.usecases.GameResult
import org.example.logic.usecases.GameStep
import org.example.logic.usecases.GetIngredientMealsUseCase
import org.example.presentation.IngredientGameUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class IngredientGameUITest {
    private lateinit var getIngredientMealsUseCase: GetIngredientMealsUseCase
    private lateinit var consoleIO: FakeConsoleIO
    private lateinit var ingredientGameUI: IngredientGameUI

    @BeforeEach
    fun setUp() {
        getIngredientMealsUseCase = mockk(relaxed = true)
    }

    @Test
    fun `should display win message when player answers all questions correctly`() {
        // Given
        val inputs = LinkedList(listOf("2", "1", "1", "1"))
        consoleIO = FakeConsoleIO(inputs)
        ingredientGameUI = IngredientGameUI(getIngredientMealsUseCase, consoleIO)

        val steps = listOf(
            GameStep("What's the main ingredient in guacamole?", listOf("Tomato", "Avocado", "Onion"), 1),
            GameStep("What's the main ingredient in hummus?", listOf("Chickpeas", "Lentils", "Beans"), 0)
        )

        every { getIngredientMealsUseCase.prepareGameSteps() } returns steps
        every {
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(1, 0))
        } returns GameResult.Win(2)

        // When
        ingredientGameUI.invoke()

        // Then
        verifySequence {
            getIngredientMealsUseCase.prepareGameSteps()
            consoleIO.write("------------------------------")
            consoleIO.write("Q1: What's the main ingredient in guacamole?")
            consoleIO.write("1- Tomato")
            consoleIO.write("2- Avocado")
            consoleIO.write("3- Onion")
            consoleIO.write("Choose answer: ")
            consoleIO.read()
            consoleIO.write("------------------------------")
            consoleIO.write("Q2: What's the main ingredient in hummus?")
            consoleIO.write("1- Chickpeas")
            consoleIO.write("2- Lentils")
            consoleIO.write("3- Beans")
            consoleIO.write("Choose answer: ")
            consoleIO.read()
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(1, 0))
            consoleIO.write("YOU WIN 🏆, you've got 2 point")
        }
    }

    @Test
    fun `should display lose message when player answers a question incorrectly`() {
        // Given
        val inputs = LinkedList(listOf("3", "dummy"))
        consoleIO = FakeConsoleIO(inputs)
        ingredientGameUI = IngredientGameUI(getIngredientMealsUseCase, consoleIO)

        val steps = listOf(
            GameStep("What's the main ingredient in guacamole?", listOf("Tomato", "Avocado", "Onion"), 1)
        )

        every { getIngredientMealsUseCase.prepareGameSteps() } returns steps
        every {
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(2))
        } returns GameResult.Lose(0)

        // When
        ingredientGameUI.invoke()

        // Then
        verifySequence {
            getIngredientMealsUseCase.prepareGameSteps()
            consoleIO.write("------------------------------")
            consoleIO.write("Q1: What's the main ingredient in guacamole?")
            consoleIO.write("1- Tomato")
            consoleIO.write("2- Avocado")
            consoleIO.write("3- Onion")
            consoleIO.write("Choose answer: ")
            consoleIO.read()
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(2))
            consoleIO.write("Wrong answer!!\nYou've got 0 point")
        }
    }

    // New test to add
    @Test
    fun `should stop asking questions after first wrong answer`() {
        // Given
        val inputs = LinkedList(listOf("3", "1")) // First answer wrong, second never used
        consoleIO = FakeConsoleIO(inputs)
        ingredientGameUI = IngredientGameUI(getIngredientMealsUseCase, consoleIO)

        val steps = listOf(
            GameStep("What's the main ingredient in guacamole?", listOf("Tomato", "Avocado", "Onion"), 1),
            GameStep("What's the main ingredient in hummus?", listOf("Chickpeas", "Lentils", "Beans"), 0)
        )

        every { getIngredientMealsUseCase.prepareGameSteps() } returns steps
        every {
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(2))
        } returns GameResult.Lose(0)

        // When
        ingredientGameUI.invoke()

        // Then
        verifySequence {
            getIngredientMealsUseCase.prepareGameSteps()
            consoleIO.write("------------------------------")
            consoleIO.write("Q1: What's the main ingredient in guacamole?")
            consoleIO.write("1- Tomato")
            consoleIO.write("2- Avocado")
            consoleIO.write("3- Onion")
            consoleIO.write("Choose answer: ")
            consoleIO.read()
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(2))
            consoleIO.write("Wrong answer!!\nYou've got 0 point")
        }
    }

    @Test
    fun `should handle invalid input`() {
        // Given
        val inputs = LinkedList(listOf("invalid", "dummy"))
        consoleIO = FakeConsoleIO(inputs)
        ingredientGameUI = IngredientGameUI(getIngredientMealsUseCase, consoleIO)

        val steps = listOf(
            GameStep("What's the main ingredient in guacamole?", listOf("Tomato", "Avocado", "Onion"), 1)
        )

        every { getIngredientMealsUseCase.prepareGameSteps() } returns steps
        every {
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(-1))
        } returns GameResult.Lose(0)

        // When
        ingredientGameUI.invoke()

        // Then
        verifySequence {
            getIngredientMealsUseCase.prepareGameSteps()
            consoleIO.write("------------------------------")
            consoleIO.write("Q1: What's the main ingredient in guacamole?")
            consoleIO.write("1- Tomato")
            consoleIO.write("2- Avocado")
            consoleIO.write("3- Onion")
            consoleIO.write("Choose answer: ")
            consoleIO.read()
            getIngredientMealsUseCase.evaluateAnswers(steps, listOf(-1))
            consoleIO.write("Wrong answer!!\nYou've got 0 point")
        }
    }

    @Test
    fun `should handle empty steps list`() {
        // Given
        val inputs = LinkedList<String>()
        consoleIO = FakeConsoleIO(inputs)
        ingredientGameUI = IngredientGameUI(getIngredientMealsUseCase, consoleIO)

        val steps = emptyList<GameStep>()

        every { getIngredientMealsUseCase.prepareGameSteps() } returns steps
        every {
            getIngredientMealsUseCase.evaluateAnswers(steps, emptyList())
        } returns GameResult.Win(0)

        // When
        ingredientGameUI.invoke()

        // Then
        verifySequence {
            getIngredientMealsUseCase.prepareGameSteps()
            getIngredientMealsUseCase.evaluateAnswers(steps, emptyList())
            consoleIO.write("YOU WIN 🏆, you've got 0 point")
        }
    }
}