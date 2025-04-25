package logic.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.GetKetoDietMealUseCase
import org.example.model.Exceptions
import org.example.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetKetoDietMealUseCaseTest {

    private lateinit var repository: MealRepository
    private lateinit var useCase: GetKetoDietMealUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetKetoDietMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal should return a keto meal when available`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        )

        // When:
        val result = useCase.getKetoMeal()

        // Then:
        assertEquals("good morning muffins", result.name)
    }

    @Test
    fun `getKetoMeal should throw exception when no keto meals are available`() {
        // Given:
        every { repository.getAllMeals() } returns emptyList()

        // When:etoMeal should throw exception when no keto meals are availab

        // Then:
        assertFailsWith<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() }
    }

    @Test
    fun `likeMeal should return current meal when a meal is suggested`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        )
        useCase.getKetoMeal()  // Suggest the meal

        // When:
        val likedMeal = useCase.likeMeal()

        // Then:
        assertEquals("good morning muffins", likedMeal.name)
    }

    @Test
    fun `likeMeal should throw exception when no meal is currently suggested`() {
        // When:

        // Then:
        assertFailsWith<Exceptions.NoMealsFoundException> { useCase.likeMeal() }
    }

    @Test
    fun `dislikeMeal should throw exception if no meal is suggested yet`() {
        // When:

        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.dislikeMeal() }
    }

    @Test
    fun `getKetoMeal should not repeat previously suggested meals`() {
        // Given:
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)),
            //createMeal(name = "dirty  broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        )

        // When:
        useCase.getKetoMeal()

        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() }
    }

    @Test
    fun `isKetoFriendly should return true for valid keto meal`() {
        val meal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        assertTrue(useCase.isKetoFriendly(meal))
    }

    @Test
    fun `isKetoFriendly should return false when carbs are too high`() {
        val meal = createMeal("alouette  potatoes", nutrition = Nutrition(368.1, 17.0, 10.0, 2.0, 14.0, 8.0, 20.0))
        assertFalse(useCase.isKetoFriendly(meal))
    }

    @Test
    fun `isKetoFriendly should return false when fat is not greater than protein`() {
        val meal = createMeal(
            "amish  tomato ketchup  for canning",
            nutrition = Nutrition(352.9, 1.0, 337.0, 23.0, 3.0, 0.0, 28.0)
        )
        assertFalse(useCase.isKetoFriendly(meal))
    }


    @Test
    fun `Meal notInSuggestedMeals should return false if already suggested`() {
        val ketoMeal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        every { repository.getAllMeals() } returns listOf(ketoMeal)

        useCase.getKetoMeal()
        // Trigger again to test if set prevents reuse
        assertFailsWith<Exceptions.NoMealsFoundException> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal should skip meals already suggested even if they are keto-friendly`() {
        val meal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        every { repository.getAllMeals() } returns listOf(meal)

        useCase.getKetoMeal()

        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal should skip meals that are not keto-friendly but not previously suggested`() {
        val nonKetoMeal = createMeal(
            "amish  tomato ketchup  for canning",
            nutrition = Nutrition(352.9, 1.0, 337.0, 23.0, 3.0, 0.0, 28.0)
        )
        every { repository.getAllMeals() } returns listOf(nonKetoMeal)

        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `isKetoFriendly should return false when nutrition is null`() {
        val meal = createMeal("Unknown Nutrition", nutrition = null)
        val result = useCase.isKetoFriendly(meal)
        assertFalse(result)
    }

    @Test
    fun `likeMeal should not change current meal`() {
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        )

        val originalMeal = useCase.getKetoMeal()
        val likedMeal = useCase.likeMeal()

        assertEquals(originalMeal, likedMeal)
        // Verify the same meal is still current
        assertEquals(originalMeal, useCase.likeMeal())
    }

    @Test
    fun `dislikeMeal should throw when no alternatives exist`() {
        // Given only one meal exists
        val onlyMeal = createMeal(
            name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)
        )
        every { repository.getAllMeals() } returns listOf(onlyMeal)

        // When we get and dislike the only meal
        useCase.getKetoMeal() // First suggestion

        // Then it should throw when trying to dislike
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.dislikeMeal()
        }
    }

//    @Test
//    fun `dislikeMeal should return a new meal when disliked`() {
//        // Given:
//        every { repository.getAllMeals() } returns listOf(
//            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)),
//            createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
//        )
//
//        // When:
//        useCase.getKetoMeal() // Gets first meal
//        val newMeal = useCase.dislikeMeal() // Dislikes first and gets second
//
//        // Then:
//        assertEquals("dirty broccoli", newMeal.name)
//    }

    @Test
    fun `likeMeal should throw when currentMeal is null`() {
        // Directly test the error case
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.likeMeal()
        }
    }

    @Test
    fun `multiple dislike operations should work correctly`() {
        // Given 3 meals (all keto-friendly)
        val meals = listOf(
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0)),
            createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0)),
            createMeal(name = "apple a day  milk shake", nutrition = Nutrition(160.2, 10.0, 55.0, 3.0, 9.0, 20.0, 7.0))
        )

        every { repository.getAllMeals() } returns meals

        // When
        val first = useCase.getKetoMeal()
        val second = useCase.dislikeMeal()
        val third = useCase.dislikeMeal()

        // Then
        val allNames = listOf(first.name, second.name, third.name)
        assertEquals(3, actual = allNames.distinct().size) // All names should be unique
        assertTrue(allNames.contains("good morning muffins"))
        assertTrue(allNames.contains("dirty broccoli"))
        assertTrue(allNames.contains("apple a day  milk shake"))
    }

    @Test
    fun `notInSuggestedMeals should return true for new meals`() {
        // Create a valid keto meal (carbs < 10, fat > protein)
        val meal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))

        every { repository.getAllMeals() } returns listOf(meal)

        // This will internally use notInSuggestedMeals
        val result = useCase.getKetoMeal()

        // Verify the meal was not previously suggested
        assertEquals("good morning muffins", result.name)
    }

    @Test
    fun `getKetoMeal should throw when all meals are disliked`() {
        // Given:
        val meal1 =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        val meal2 = createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        // When:
        useCase.getKetoMeal() // meal1
        useCase.dislikeMeal() // dislikes meal1, gets meal2

        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() } // dislikes meal2
    }

    @Test
    fun `isKetoFriendly should return false when carbs are exactly 10`() {
        val meal = createMeal("boundary case", nutrition = Nutrition(200.0, 10.0, 20.0, 15.0, 15.0, 5.0, 5.0))
        assertFalse(useCase.isKetoFriendly(meal))
    }

    @Test
    fun `isKetoFriendly should return false when fat equals protein`() {
        val meal = createMeal("equal fat and protein", nutrition = Nutrition(200.0, 5.0, 15.0, 15.0, 15.0, 5.0, 5.0))
        assertFalse(useCase.isKetoFriendly(meal))
    }

    @Test
    fun `init should load available meals`() {
        // Given
        val meal = createMeal(
            "amish  tomato ketchup  for canning",
            nutrition = Nutrition(352.9, 1.0, 337.0, 23.0, 3.0, 0.0, 28.0)
        )
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val newUseCase = GetKetoDietMealUseCase(repository)

        // Then
        verify { repository.getAllMeals() }
    }

    @Test
    fun `dislikeMeal should properly track disliked meals`() {
        // Given
        val meal1 =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        val meal2 = createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        // When
        useCase.getKetoMeal() // meal1
        useCase.dislikeMeal() // dislikes meal1, gets meal2

        // Then - verify meal1 is in disliked set
        assertThrows<Exceptions.NoMealsFoundException> {
            useCase.getKetoMeal() // should throw since meal1 is disliked and meal2 was suggested
        }
    }

    @Test
    fun `reloadMeals should throw when no keto meals remain`() {
        // Given
        val meal1 =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        val meal2 = createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        // When
        useCase.getKetoMeal() // meal1
        useCase.dislikeMeal() // dislikes meal1

        // Then - should throw because meal2 isn't keto-friendly
        assertThrows<Exceptions.NoMealsFoundException> { useCase.getKetoMeal() }
    }

    @Test
    fun `isKetoFriendly should handle null nutrition values`() {
        // Given
        val meal = createMeal("no nutrition", nutrition = null)

        // When/Then
        assertFalse(useCase.isKetoFriendly(meal))
    }

//    @Test
//    fun `dislikeMeal should clear current meal and get new one`() {
//        // Given
//        val meal1 = createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
//        val meal2 = createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
//        every { repository.getAllMeals() } returns listOf(meal1, meal2)
//        useCase.getKetoMeal() // gets meal1
//
//        // When
//        val newMeal = useCase.dislikeMeal() // dislikes meal1, gets meal2
//
//        // Then
//        assertEquals("dirty broccoli", newMeal.name)
//        assertEquals("dirty broccoli", useCase.likeMeal().name) // verify current meal is meal2
//    }

    @Test
    fun `getKetoMeal should apply all filters correctly`() {
        // Given
        val suggestedMeal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        val dislikedMeal =
            createMeal(name = "dirty broccoli", nutrition = Nutrition(137.1, 11.0, 11.0, 10.0, 10.0, 5.0, 4.0))
        val nonKetoMeal = createMeal(
            "amish  tomato ketchup  for canning",
            nutrition = Nutrition(352.9, 1.0, 337.0, 23.0, 3.0, 0.0, 28.0)
        )
        val validMeal =
            createMeal(name = "apple a day  milk shake", nutrition = Nutrition(160.2, 10.0, 55.0, 3.0, 9.0, 20.0, 7.0))

        // Setup initial state directly (since we're testing filtering, not the state management)
        val useCase = GetKetoDietMealUseCase(repository).apply {
            // Access private fields via reflection for testing purposes
            val suggestedField = GetKetoDietMealUseCase::class.java.getDeclaredField("suggestedMeals")
            suggestedField.isAccessible = true
            suggestedField.set(this, mutableSetOf(suggestedMeal))

            val dislikedField = GetKetoDietMealUseCase::class.java.getDeclaredField("dislikedMeals")
            dislikedField.isAccessible = true
            dislikedField.set(this, mutableSetOf(dislikedMeal))
        }

        every { repository.getAllMeals() } returns listOf(suggestedMeal, dislikedMeal, nonKetoMeal, validMeal)

        // When
        val result = useCase.getKetoMeal()

        // Then
        assertEquals("apple a day  milk shake", result.name)
    }

    @Test
    fun `isKetoFriendly should return true when fat is slightly greater than protein`() {
        val meal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        assertTrue(useCase.isKetoFriendly(meal))
    }

    @Test
    fun `dislikeMeal should throw when no alternative keto meals exist`() {
        // Given:
        val dislikedMeal =
            createMeal(name = "good morning muffins", nutrition = Nutrition(215.9, 15.0, 57.0, 7.0, 7.0, 30.0, 9.0))
        val nonKetoMeal = createMeal(
            "amish  tomato ketchup  for canning",
            nutrition = Nutrition(352.9, 1.0, 337.0, 23.0, 3.0, 0.0, 28.0)
        )
        every { repository.getAllMeals() } returns listOf(dislikedMeal, nonKetoMeal)

        // When:
        useCase.getKetoMeal() // gets and dislikes the only keto meal


        // Then:
        assertThrows<Exceptions.NoMealsFoundException> { useCase.dislikeMeal() }
    }
}