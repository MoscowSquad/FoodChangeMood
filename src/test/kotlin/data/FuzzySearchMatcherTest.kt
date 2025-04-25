package data

import com.google.common.truth.Truth
import org.example.data.FuzzySearchMatcher
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.MATCH_EXACTLY
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.NOT_MATCHED
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FuzzySearchMatcherTest {
    private lateinit var fuzzySearchMatcher: FuzzySearchMatcher

    @BeforeEach
    fun setUp() {
        fuzzySearchMatcher = FuzzySearchMatcher(3)
    }

    @Test
    fun `Should return matching accuracy MATCH_EXACTLY when the text equal the keyword`() {
        // Given
        val text = "Test"
        val keyword = "Test"

        // When
        val accuracy = fuzzySearchMatcher.getMatchAccuracy(text, keyword)

        // Then
        Truth.assertThat(accuracy).isEqualTo(MATCH_EXACTLY)
    }


    @Test
    fun `Should return matching accuracy when the text equals the keyword with acceptable typos`() {
        // Given
        val text = "just random function"
        val keyword = "just  ranom fanction"

        // When
        val accuracy = fuzzySearchMatcher.getMatchAccuracy(text, keyword)

        // Then
        Truth.assertThat(accuracy).isGreaterThan(0)
    }


    @ParameterizedTest
    @ValueSource(strings = ["are", "you ready", "random test", "game gam game gam", "move move and move", "city cat hit the rat"])
    fun `Should return matching accuracy NOT_MATCHED when the text not contain the keyword`(keyword: String) {
        // Given
        val text = "This is just a random text to test this function"

        // When
        val accuracy = fuzzySearchMatcher.getMatchAccuracy(text, keyword)

        // Then
        Truth.assertThat(accuracy).isEqualTo(NOT_MATCHED)
    }

    @Test
    fun `Should throw EmptyKeywordException when the keyword is empty`() {
        // Given
        val text = "Test"
        val keyword = ""

        // When && Then
        assertThrows<Exceptions.EmptyKeywordException> {
            fuzzySearchMatcher.getMatchAccuracy(text, keyword)
        }
    }

    @Test
    fun `Should throw EmptyTextException when the keyword is empty`() {
        // Given
        val text = ""
        val keyword = "Test"

        // When && Then
        assertThrows<Exceptions.EmptyTextException> {
            fuzzySearchMatcher.getMatchAccuracy(text, keyword)
        }
    }
}