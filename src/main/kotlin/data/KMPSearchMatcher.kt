package org.example.data

import org.example.logic.repository.Accuracy
import org.example.logic.repository.SearchMatcher
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.MATCH_PARTIAL
import org.example.logic.usecases.SearchMealByNameUseCase.Companion.NOT_MATCHED
import org.example.model.Exceptions

class KMPSearchMatcher : SearchMatcher {
    override fun getMatchAccuracy(text: String, keyword: String): Accuracy {
        if (keyword.isEmpty())
            throw Exceptions.EmptyKeywordException()

        val formattedText = text.trim().lowercase()
        val formattedKeyword = keyword.trim().lowercase()
        return searchByKnuthMorrisPrattAlgorithm(formattedText, formattedKeyword)
    }

    private fun searchByKnuthMorrisPrattAlgorithm(text: String, keyword: String): Accuracy {
        val lps = constructLPS(keyword)
        val textLength = text.length
        val keywordLength = keyword.length

        var i = 0
        var j = 0
        while (i < textLength) {
            if (text[i] == keyword[j]) {
                i++; j++
                if (j == keywordLength)
                    return MATCH_PARTIAL
            } else if (j != 0) {
                j = lps[j - 1]
            } else {
                i++
            }
        }
        return NOT_MATCHED
    }

    private fun constructLPS(keyword: String): IntArray {
        val lps = IntArray(keyword.length)
        lps[0] = 0

        var j = 0
        var i = 1
        while (i < keyword.length) {
            if (keyword[i] == keyword[j]) {
                lps[i++] = ++j
            } else if (j != 0) {
                j = lps[j - 1]
            } else {
                lps[i++] = 0
            }
        }
        return lps
    }
}