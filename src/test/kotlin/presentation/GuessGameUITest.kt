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
import org.junit.jupiter.api.assertThrows
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

 @Test
 fun `invoke should display welcome message and prompt for guess`() {
  // Given
  val testMeal = createMeal("Pizza", minutes = 30)
  every { mockRandomMealUseCase.getRandomMeal() } returns testMeal
  testConsoleIO.addInput("30")

  // When
  guessGameUI()

  // Then
  val output = testConsoleIO.getOutput()
  assertTrue(output[0] == "Prepare a meal to guess by you ...")
  assertTrue(output[1] == "Guess the preparation time for (Pizza): ")
  assertTrue(output[2] == "Preparation time:")
  assertTrue(output[3] == "You are correct")
 }

 @Test
 fun `guessGame should accept correct guess on first attempt`() {
  // Given
  val testMeal = createMeal("Pasta", minutes = 15)
  testConsoleIO.addInput("15")

  // When
  guessGameUI.guessGame(testMeal)

  // Then
  val output = testConsoleIO.getOutput()
  assertEquals("Preparation time:", output[0])
  assertEquals("You are correct", output[1])
  assertEquals(2, output.size)
 }

 @Test
 fun `guessGame should allow multiple attempts until correct`() {
  // Given
  val testMeal = createMeal("Salad", minutes = 10)
  testConsoleIO.addInput("5", "8", "10")

  // When
  guessGameUI.guessGame(testMeal)

  // Then
  val output = testConsoleIO.getOutput()
  assertEquals("Preparation time:", output[0])
  assertEquals("Not correct. Guess again, ", output[1])
  assertEquals("Preparation time:", output[2])
  assertEquals("Not correct. Guess again, ", output[3])
  assertEquals("Preparation time:", output[4])
  assertEquals("You are correct", output[5])
 }

 @Test
 fun `guessGame should end after 3 incorrect attempts`() {
  // Given
  val testMeal = createMeal("Burger", minutes = 20)
  testConsoleIO.addInput("10", "15", "25")

  // When
  guessGameUI.guessGame(testMeal)

  // Then
  val output = testConsoleIO.getOutput()
  assertEquals(7, output.size)
  assertEquals("later", output.last())
  assertEquals(3, output.count { it == "Not correct. Guess again, " })
 }

 @Test
 fun `should throw exception when meal provider fails`() {
  // Given
  every { mockRandomMealUseCase.getRandomMeal() } throws Exceptions.NoMealsFoundException("No meals available")

  // When & Then
  assertThrows<Exceptions.NoMealsFoundException> {
   guessGameUI()
  }
  assertEquals(1, testConsoleIO.getOutput().size)
  assertEquals("Prepare a meal to guess by you ...", testConsoleIO.getOutput()[0])
 }

 @Test
 fun `should handle invalid number input gracefully`() {
  // Given
  val testMeal = createMeal("Steak", minutes = 25)
  testConsoleIO.addInput("invalid", "25")

  // When
  guessGameUI.guessGame(testMeal)

  // Then
  val output = testConsoleIO.getOutput()
  assertEquals("Preparation time:", output[0])
  assertEquals("Not correct. Guess again, ", output[1])
  assertEquals("Preparation time:", output[2])
  assertEquals("You are correct", output[3])
 }

 private class TestConsoleIO : ConsoleIO {
  private val inputs = mutableListOf<String>()
  private val outputs = mutableListOf<String>()

  fun addInput(vararg lines: String) {
   inputs.addAll(lines)
  }

  fun getOutput(): List<String> = outputs

  override fun read(): String {
   if (inputs.isEmpty()) throw IllegalStateException("No more test inputs available")
   return inputs.removeAt(0)
  }

  override fun write(message: String?) {
   outputs.add(message ?: "")
  }
 }
 
}