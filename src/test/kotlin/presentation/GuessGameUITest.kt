package presentation

 import io.mockk.*
 import logic.usecases.createMeal
 import org.example.logic.usecases.RandomMealNameProviderUseCase
 import org.example.model.Exceptions
 import org.example.presentation.GuessGameUI
 import org.example.presentation.io.ConsoleIO
 import org.junit.jupiter.api.BeforeEach
 import org.junit.jupiter.api.Test

 class GuessGameUITest {
     private lateinit var randomMealNameProviderUseCase: RandomMealNameProviderUseCase
     private lateinit var consoleIO: ConsoleIO
     private lateinit var guessGameUI: GuessGameUI

     @BeforeEach
     fun setUp() {
         randomMealNameProviderUseCase = mockk(relaxed = true)
         consoleIO = mockk(relaxed = true)
         guessGameUI = GuessGameUI(randomMealNameProviderUseCase, consoleIO)
     }

     @Test
     fun `guessGame should catch IllegalStateException from consoleIO read`() {
         // Given
         val testMeal = createMeal("Sushi", 12)
         every { consoleIO.write(any()) } just runs
         every { consoleIO.read() } throws IllegalStateException("Simulated input failure")

         // When
         guessGameUI.guessGame(testMeal)

         // Then
         verifySequence {
             consoleIO.write("Preparation time:")
             consoleIO.read()
             consoleIO.write("Error: No input provided")
         }
     }

     @Test
     fun `invoke should display welcome and prompt for guess`() {
         // Given
         val testMeal = createMeal("Pizza", 30)
         every { randomMealNameProviderUseCase.getRandomMeal() } returns testMeal
         every { consoleIO.read() } returns "30"
         every { randomMealNameProviderUseCase.isSuggestRight(30) } returns true

         // When
         guessGameUI()

         // Then
         verifySequence {
             consoleIO.write("Prepare a meal to guess by you ...")
             randomMealNameProviderUseCase.getRandomMeal()
             consoleIO.write("Guess the preparation time for (Pizza): ")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(30)
             consoleIO.write("You are correct")
         }
     }

     @Test
     fun `guessGame should accept correct first attempt`() {
         // Given
         val testMeal = createMeal("Pasta", 15)
         every { consoleIO.read() } returns "15"
         every { randomMealNameProviderUseCase.isSuggestRight(15) } returns true

         // When
         guessGameUI.guessGame(testMeal)

         // Then
         verifySequence {
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(15)
             consoleIO.write("You are correct")
         }
     }

     @Test
     fun `guessGame should handle multiple attempts`() {
         // Given
         val testMeal = createMeal("Salad", 10)
         every { consoleIO.read() } returnsMany listOf("5", "8", "10")
         every { randomMealNameProviderUseCase.isSuggestRight(5) } returns false
         every { randomMealNameProviderUseCase.isSuggestRight(8) } returns false
         every { randomMealNameProviderUseCase.isSuggestRight(10) } returns true

         // When
         guessGameUI.guessGame(testMeal)

         // Then
         verifySequence {
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(5)
             consoleIO.write("Not correct. Guess again, ")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(8)
             consoleIO.write("Not correct. Guess again, ")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(10)
             consoleIO.write("You are correct")
         }
     }

     @Test
     fun `invoke should handle meal provider exception`() {
         // Given
         every { randomMealNameProviderUseCase.getRandomMeal() } throws Exceptions.NoMealsFoundException("No meals available")

         // When
         guessGameUI()

         // Then
         verifySequence {
             consoleIO.write("Prepare a meal to guess by you ...")
             randomMealNameProviderUseCase.getRandomMeal()
             consoleIO.write("Error: No meals available")
         }
     }

     @Test
     fun `guessGame should end after 3 incorrect attempts`() {
         // Given
         val testMeal = createMeal("Burger", 20)
         every { consoleIO.read() } returnsMany listOf("10", "15", "25")
         every { randomMealNameProviderUseCase.isSuggestRight(any()) } returns false

         // When
         guessGameUI.guessGame(testMeal)

         // Then
         verifySequence {
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(10)
             consoleIO.write("Not correct. Guess again, ")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(15)
             consoleIO.write("Not correct. Guess again, ")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(25)
             consoleIO.write("Not correct. Guess again, ")
             consoleIO.write("later")
         }
     }

     @Test
     fun `should handle invalid number input`() {
         // Given
         val testMeal = createMeal("Steak", 25)
         every { consoleIO.read() } returnsMany listOf("invalid", "25")
         every { randomMealNameProviderUseCase.isSuggestRight(null) } throws NumberFormatException("Invalid number")
         every { randomMealNameProviderUseCase.isSuggestRight(25) } returns true

         // When
         guessGameUI.guessGame(testMeal)

         // Then
         verifySequence {
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(null)
             consoleIO.write("Invalid input. Please enter a number.")
             consoleIO.write("Preparation time:")
             consoleIO.read()
             randomMealNameProviderUseCase.isSuggestRight(25)
             consoleIO.write("You are correct")
         }
     }
 }