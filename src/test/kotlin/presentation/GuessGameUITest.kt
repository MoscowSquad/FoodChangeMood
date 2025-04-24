package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecases.createMeal
import org.example.logic.usecases.RandomMealNameProviderUseCase
import org.example.model.Exceptions
import org.example.presentation.GuessGameUI
import org.example.presentation.io.ConsoleIO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GuessGameUITest {
 private val mockRandomMealUseCase: RandomMealNameProviderUseCase = mockk()
 private val testConsoleIO = TestConsoleIO()
 private lateinit var guessGameUI: GuessGameUI

 @BeforeEach
 fun setUp() {
  guessGameUI = GuessGameUI(mockRandomMealUseCase, testConsoleIO)
 }

 // Happy path tests
 @Test
 fun `invoke should display welcome and prompt for guess`() {
  val testMeal = createMeal("Pizza", 30)
  every { mockRandomMealUseCase.getRandomMeal() } returns testMeal
  every { mockRandomMealUseCase.isSuggestRight(30) } returns true
  testConsoleIO.addInput("30")

  guessGameUI()

  val output = testConsoleIO.getOutput()
  assertEquals("Prepare a meal to guess by you ...", output[0])
  assertEquals("Guess the preparation time for (Pizza): ", output[1])
  assertEquals("Preparation time:", output[2])
  assertEquals("You are correct", output[3])
 }

 @Test
 fun `guessGame should accept correct first attempt`() {
  val testMeal = createMeal("Pasta", 15)
  every { mockRandomMealUseCase.isSuggestRight(15) } returns true
  testConsoleIO.addInput("15")

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
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
  testConsoleIO.addInput("5", "8", "10")

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
  assertEquals(6, output.size)
  assertEquals("You are correct", output.last())
 }

 // Error handling tests
 @Test
 fun `invoke should handle meal provider exception`() {
  every { mockRandomMealUseCase.getRandomMeal() } throws
          Exceptions.NoMealsFoundException("No meals available")
  testConsoleIO.addInput("") // Dummy input to prevent IllegalStateException

  guessGameUI()

  val output = testConsoleIO.getOutput()
  assertEquals(2, output.size)
  assertEquals("Prepare a meal to guess by you ...", output[0])
  assertEquals("Error: No meals available", output[1])
 }

 @Test
 fun `guessGame should end after 3 incorrect attempts`() {
  val testMeal = createMeal("Burger", 20)
  every { mockRandomMealUseCase.isSuggestRight(any()) } returns false
  testConsoleIO.addInput("10", "15", "25")

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
  assertEquals(7, output.size)
  assertEquals("later", output.last())
 }

 @Test
 fun `should handle invalid number input`() {
  val testMeal = createMeal("Steak", 25)
  every { mockRandomMealUseCase.isSuggestRight(null) } throws
          NumberFormatException("Invalid number")
  every { mockRandomMealUseCase.isSuggestRight(25) } returns true
  testConsoleIO.addInput("invalid", "25")

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
  assertEquals("Invalid input. Please enter a number.", output[1])
  assertEquals("You are correct", output.last())
 }

 @Test
 fun `should handle no input available`() {
  val testMeal = createMeal("Fish", 12)
  // Don't add any inputs to simulate empty input
  testConsoleIO.addInput() // Empty input queue

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
  assertEquals("Error: No input provided", output.last())
 }

 @Test
 fun `should handle suggestion validation exception`() {
  val testMeal = createMeal("Chicken", 15)
  every { mockRandomMealUseCase.isSuggestRight(any()) } throws
          Exceptions.NoMealsFoundException("Validation failed")
  testConsoleIO.addInput("15", "exit") // Provide enough inputs

  guessGameUI.guessGame(testMeal)

  val output = testConsoleIO.getOutput()
  assertTrue(output.any { it.startsWith("Error:") || it.contains("Invalid input") })
 }

 // Test ConsoleIO implementation
 private class TestConsoleIO : ConsoleIO {
  private val inputs = mutableListOf<String>()
  private val outputs = mutableListOf<String>()

  fun addInput(vararg lines: String) {
   inputs.addAll(lines)
  }

  fun getOutput(): List<String> = outputs

  override fun read(): String {
   if (inputs.isEmpty()) throw IllegalStateException("No more test inputs")
   return inputs.removeAt(0)
  }

  override fun write(message: String?) {
   outputs.add(message ?: "")
  }
 }
}