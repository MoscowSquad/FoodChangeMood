package data

import com.google.common.truth.Truth
import org.example.data.KMPSearchMatcher
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.MATCH_PARTIAL
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.NOT_MATCHED
import org.example.model.Exceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class KMPSearchMatcherTest {
    private lateinit var kmpSearchMatcherTest: KMPSearchMatcher

    @BeforeEach
    fun setUp() {
        kmpSearchMatcherTest = KMPSearchMatcher()
    }

    @Test
    fun `Should return matching accuracy MATCH_PARTIAL when the text equal the keyword`() {
        // Given
        val text = "Test"
        val keyword = "Test"

        // When
        val accuracy = kmpSearchMatcherTest.getMatchAccuracy(text, keyword)

        // Then
        Truth.assertThat(accuracy).isEqualTo(MATCH_PARTIAL)
    }


    @ParameterizedTest
    @ValueSource(strings = ["just", "random", "function", "random text to test", "game gam game gam"])
    fun `Should return matching accuracy MATCH_PARTIAL when the text contain the keyword`(keyword: String) {
        // Given
        val text = "This is just a random text to test this function, game gam game gam"

        // When
        val accuracy = kmpSearchMatcherTest.getMatchAccuracy(text, keyword)

        // Then
        Truth.assertThat(accuracy).isEqualTo(MATCH_PARTIAL)
    }


    @ParameterizedTest
    @ValueSource(strings = ["are", "you ready", "random test", "game gam game gam", "move move and move", "city cat hit the rat"])
    fun `Should return matching accuracy NOT_MATCHED when the text not contain the keyword`(keyword: String) {
        // Given
        val text = "This is just a random text to test this function"

        // When
        val accuracy = kmpSearchMatcherTest.getMatchAccuracy(text, keyword)

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
            kmpSearchMatcherTest.getMatchAccuracy(text, keyword)
        }
    }


}