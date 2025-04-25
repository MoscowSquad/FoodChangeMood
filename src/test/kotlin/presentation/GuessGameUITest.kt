package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
import org.example.presentation.GuessGameUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class GuessGameUITest {
 private val mockRandomMealUseCase: RandomMealNameProviderUseCase = mockk()
 private lateinit var consoleIO: FakeConsoleIO
 private lateinit var guessGameUI: GuessGameUI

 @BeforeEach
 fun setUp() {
  consoleIO = FakeConsoleIO(LinkedList())
  guessGameUI = GuessGameUI(mockRandomMealUseCase, consoleIO)
 }
 @Test
 fun `guessGame should catch IllegalStateException from consoleIO read`() {
  val testMeal = createMeal("Sushi", 12)

  consoleIO = object : FakeConsoleIO(LinkedList()) {
   override fun read(): String {
    throw IllegalStateException("Simulated input failure")
   }
  }
  guessGameUI = GuessGameUI(mockRandomMealUseCase, consoleIO)

  guessGameUI.guessGame(testMeal)

  val output = consoleIO.outputs
  assertEquals("Preparation time:", output[0])
  assertEquals("Error: No input provided", output[1])
 }

 @Test
 fun `invoke should display welcome and prompt for guess`() {
  val testMeal = createMeal("Pizza", 30)
  every { mockRandomMealUseCase.getRandomMeal() } returns testMeal
  every { mockRandomMealUseCase.isSuggestRight(30) } returns true
  consoleIO.inputs.add("30")

  guessGameUI()

  val output = consoleIO.outputs
  assertEquals("Prepare a meal to guess by you ...", output[0])
  assertEquals("Guess the preparation time for (Pizza): ", output[1])
  assertEquals("Preparation time:", output[2])
  assertEquals("You are correct", output[3])
 }

 @Test
 fun `guessGame should accept correct first attempt`() {
  val testMeal = createMeal("Pasta", 15)
  every { mockRandomMealUseCase.isSuggestRight(15) } returns true
  consoleIO.inputs.add("15")

  guessGameUI.guessGame(testMeal)

  val output = consoleIO.outputs
  assertEquals(2, output.size)
  assertEquals("Preparation time:", output[0])
  assertEquals("You are correct", output[1])
 }

 @Test
 fun `guessGame should handle multiple attempts`() {
  val testMeal = createMeal("Salad", 10)
  every { mockRandomMealUseCase.isSuggestRight(5) } returns false
  every { mockRandomMealUseCase.isSuggestRight(8) } returns false
  every { mockRandomMealUseCase.isSuggestRight(10) } returns true
  consoleIO.inputs.addAll(listOf("5", "8", "10"))

  guessGameUI.guessGame(testMeal)

  val output = consoleIO.outputs
  assertEquals(6, output.size)
  assertEquals("You are correct", output.last())
 }

 @Test
 fun `invoke should handle meal provider exception`() {
  every { mockRandomMealUseCase.getRandomMeal() } throws Exceptions.NoMealsFoundException("No meals available")
  consoleIO.inputs.add("") // Dummy input

  guessGameUI()

  val output = consoleIO.outputs
  assertEquals(2, output.size)
  assertEquals("Prepare a meal to guess by you ...", output[0])
  assertEquals("Error: No meals available", output[1])
 }

 @Test
 fun `guessGame should end after 3 incorrect attempts`() {
  val testMeal = createMeal("Burger", 20)
  every { mockRandomMealUseCase.isSuggestRight(any()) } returns false
  consoleIO.inputs.addAll(listOf("10", "15", "25"))

  guessGameUI.guessGame(testMeal)

  val output = consoleIO.outputs
  assertEquals(7, output.size)
  assertEquals("later", output.last())
 }

 @Test
 fun `should handle invalid number input`() {
  val testMeal = createMeal("Steak", 25)
  every { mockRandomMealUseCase.isSuggestRight(null) } throws NumberFormatException("Invalid number")
  every { mockRandomMealUseCase.isSuggestRight(25) } returns true
  consoleIO.inputs.addAll(listOf("invalid", "25"))

  guessGameUI.guessGame(testMeal)

  val output = consoleIO.outputs
  assertEquals("Invalid input. Please enter a number.", output[1])
  assertEquals("You are correct", output.last())
 }
}
