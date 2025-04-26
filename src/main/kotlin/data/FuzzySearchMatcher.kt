package org.example.data

import org.example.logic.repository.Accuracy
import org.example.logic.repository.SearchMatcher
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.NOT_MATCHED
import org.example.model.Exceptions
import kotlin.math.min

class FuzzySearchMatcher(private val threshold: Int = 2) : SearchMatcher {
    @Throws(
        Exceptions.EmptyTextException::class,
        Exceptions.EmptyKeywordException::class,
    )
    override fun getMatchAccuracy(text: String, keyword: String): Accuracy {
        if (text.isEmpty())
            throw Exceptions.EmptyTextException()

        if (keyword.isEmpty())
            throw Exceptions.EmptyKeywordException()

        val accuracy = searchByLevenshteinAlgorithm(text, keyword)
        return if (accuracy <= threshold) accuracy else NOT_MATCHED
    }

    private fun searchByLevenshteinAlgorithm(text: String, keyword: String): Accuracy {
        val formattedText = text.trim().lowercase()
        val formattedKeyword = keyword.trim().lowercase()
        val depth = prepareBaseArray(formattedText, formattedKeyword)

        (1..formattedText.length).forEach { i ->
            (1..formattedKeyword.length).forEach { j ->
                if (formattedText[i - 1] == formattedKeyword[j - 1]) {
                    depth[i][j] = depth[i - 1][j - 1]
                } else {
                    val min = min(
                        min(depth[i - 1][j], depth[i][j - 1]),
                        depth[i - 1][j - 1]
                    )
                    depth[i][j] = (1 + min)
                }
            }
        }

        return depth[formattedText.length][formattedKeyword.length]
    }

    private fun prepareBaseArray(formattedText: String, formattedKeyword: String): Array<IntArray> {
        val depth = Array(formattedText.length + 1) { IntArray(formattedKeyword.length + 1) }

        (0..formattedText.length).forEach { i ->
            depth[i][0] = i
        }
        (0..formattedKeyword.length).forEach { j ->
            depth[0][j] = j
        }

        return depth
    }
}