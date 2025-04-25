package logic.usecases

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.data.KMPSearchMatcher
import org.example.logic.repository.MealRepository
import org.example.logic.repository.SearchMatcher
import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SearchMealByNameUseCaseTest {
    private lateinit var searchMatcher: SearchMatcher
    private lateinit var repository: MealRepository
    private lateinit var searchMealByNameUseCaseTest: SearchMealByNameUseCase

    @BeforeEach
    fun setUp() {
        searchMatcher = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        searchMealByNameUseCaseTest = SearchMealByNameUseCase(searchMatcher, repository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should return meal when search for meal with full correct name`(keyword: String) {
        // Given
        every { searchMatcher.getMatchAccuracy(any(), any()) } answers {
            val mealName = firstArg<String>()
            KMPSearchMatcher().getMatchAccuracy(mealName, keyword)
        }

        setupMealsByNames(
            "Spaghetti Bolognese",
            "Chicken Tikka Masala",
            "Grilled Salmon with Lemon Butter",
            "Vegetable Stir-Fry",
            "Beef Tacos",
            "Margherita Pizza",
            "Shrimp Pad Thai",
            "Quinoa Salad",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        Truth.assertThat(result.name)
            .isAnyOf("Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad")
    }

    @ParameterizedTest
    @ValueSource(strings = ["Spaghetti", "ef Ta", "Salad", "Pad Thai"])
    fun `should return meal when searching for a meal with the partial correct name`(keyword: String) {
        // Given
        setupMealsByNames(
            "Spaghetti Bolognese",
            "Chicken Tikka Masala",
            "Grilled Salmon with Lemon Butter",
            "Vegetable Stir-Fry",
            "Beef Tacos",
            "Margherita Pizza",
            "Shrimp Pad Thai",
            "Quinoa Salad",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        Truth.assertThat(result.name)
            .isAnyOf("Spaghetti Bolognese", "Beef Tacos", "Shrimp Pad Thai", "Quinoa Salad")
    }

    @Test
    fun `should throw EmptyKeywordException when the keyword is empty`() {
        // Given
        val keyword = ""
        every { searchMatcher.getMatchAccuracy(any(), any()) } answers {
            val mealName = firstArg<String>()
            KMPSearchMatcher().getMatchAccuracy(mealName, keyword)
        }

        setupMealsByNames(
            "Spaghetti Bolognese",
            "Chicken Tikka Masala",
            "Grilled Salmon with Lemon Butter",
            "Vegetable Stir-Fry",
            "Beef Tacos",
            "Margherita Pizza",
            "Shrimp Pad Thai",
            "Quinoa Salad",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )


        // When, Then
        assertThrows<Exceptions.EmptyKeywordException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should throw KeywordNotFoundException when there is no meal match the keyword`(keyword: String) {
        // Given
        every { searchMatcher.getMatchAccuracy(any(), any()) } answers {
            val mealName = firstArg<String>()
            KMPSearchMatcher().getMatchAccuracy(mealName, keyword)
        }

        setupMealsByNames(
            "Chicken Tikka Masala",
            "Grilled Salmon with Lemon Butter",
            "Vegetable Stir-Fry",
            "Margherita Pizza",
            "Shrimp Pad Thai",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )

        // When, Then
        assertThrows<Exceptions.KeywordNotFoundException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Salad", "Pizza", "Salmon"])
    fun `should throw KeywordNotFoundException when there is no meal match the keyword even partially`(keyword: String) {
        // Given
        every { searchMatcher.getMatchAccuracy(any(), any()) } answers {
            val mealName = firstArg<String>()
            KMPSearchMatcher().getMatchAccuracy(mealName, keyword)
        }

        setupMealsByNames(
            "Spaghetti Bolognese",
            "Chicken Tikka Masala",
            "Vegetable Stir-Fry",
            "Beef Tacos",
            "Shrimp Pad Thai",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )

        // When, Then
        assertThrows<Exceptions.KeywordNotFoundException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should verify using searchMatcher when searching by the keyword `(keyword: String) {
        // Given
        setupMealsByNames(
            "Spaghetti Bolognese",
            "Chicken Tikka Masala",
            "Grilled Salmon with Lemon Butter",
            "Vegetable Stir-Fry",
            "Beef Tacos",
            "Margherita Pizza",
            "Shrimp Pad Thai",
            "Quinoa Salad",
            "Lamb Rogan Josh",
            "Mushroom Risotto",
            null
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        verify { searchMatcher.getMatchAccuracy(result.name!!, keyword) }
    }

    private fun setupMealsByNames(vararg names: String?) {
        names.map {
            createMeal(it)
        }.also { meals ->
            every { repository.getAllMeals() } returns meals
        }
    }
}