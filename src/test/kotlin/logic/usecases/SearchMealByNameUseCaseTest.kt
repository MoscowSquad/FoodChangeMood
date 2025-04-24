package logic.usecases

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.data.KMPSearchMatcher
import org.example.logic.repository.MealRepository
import org.example.logic.usecases.SearchMealByNameUseCase
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SearchMealByNameUseCaseTest {
    private lateinit var searchMatcher: KMPSearchMatcher
    private lateinit var repository: MealRepository
    private lateinit var searchMealByNameUseCaseTest: SearchMealByNameUseCase

    @BeforeEach
    fun setUp() {
        searchMatcher = KMPSearchMatcher()
        repository = mockk(relaxed = true)
        searchMealByNameUseCaseTest = SearchMealByNameUseCase(searchMatcher, repository)
    }

    @ParameterizedTest()
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should return meal when search for meal with full correct name`(keyword: String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Spaghetti Bolognese"),
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Grilled Salmon with Lemon Butter"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Beef Tacos"),
            createMeal(name = "Margherita Pizza"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Quinoa Salad"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        Truth.assertThat(result.name)
            .isAnyOf("Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad")
    }

    @ParameterizedTest()
    @ValueSource(strings = ["Spaghetti", "ef Ta", "Salad", "Pad Thai"])
    fun `should return meal when searching for a meal with the partial correct name`(keyword: String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Spaghetti Bolognese"),
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Grilled Salmon with Lemon Butter"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Beef Tacos"),
            createMeal(name = "Margherita Pizza"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Quinoa Salad"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        Truth.assertThat(result.name)
            .isAnyOf("Spaghetti Bolognese", "Beef Tacos", "Shrimp Pad Thai", "Quinoa Salad")
    }

    @Test()
    fun `should throw EmptyKeywordException when the keyword is empty`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Spaghetti Bolognese"),
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Grilled Salmon with Lemon Butter"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Beef Tacos"),
            createMeal(name = "Margherita Pizza"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Quinoa Salad"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        val keyword = ""

        // When, Then
        assertThrows<Exceptions.EmptyKeywordException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should throw KeywordNotFoundException when there is no meal match the keyword`(keyword: String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Grilled Salmon with Lemon Butter"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Margherita Pizza"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        // When, Then
        assertThrows<Exceptions.KeywordNotFoundException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = ["Salad", "Pizza", "Salmon"])
    fun `should throw KeywordNotFoundException when there is no meal match the keyword even partially`(keyword: String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Spaghetti Bolognese"),
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Beef Tacos"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        // When, Then
        assertThrows<Exceptions.KeywordNotFoundException> {
            searchMealByNameUseCaseTest.search(keyword)
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = ["Spaghetti Bolognese", "Beef Tacos", "Quinoa Salad"])
    fun `should verify using searchMatcher when searching by the keyword `(keyword: String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(name = "Spaghetti Bolognese"),
            createMeal(name = "Chicken Tikka Masala"),
            createMeal(name = "Grilled Salmon with Lemon Butter"),
            createMeal(name = "Vegetable Stir-Fry"),
            createMeal(name = "Beef Tacos"),
            createMeal(name = "Margherita Pizza"),
            createMeal(name = "Shrimp Pad Thai"),
            createMeal(name = "Quinoa Salad"),
            createMeal(name = "Lamb Rogan Josh"),
            createMeal(name = "Mushroom Risotto"),
            createMeal(name = null)
        )

        // When
        val result = searchMealByNameUseCaseTest.search(keyword)

        // Then
        verify { searchMatcher.getMatchAccuracy(result.name!!, keyword) }
    }
}