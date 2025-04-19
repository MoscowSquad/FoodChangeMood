package org.example.data

import org.example.logic.SearchMatcher
import org.example.logic.SearchMealByNameUseCase.Companion.MATCH_PARTIAL
import org.example.logic.SearchMealByNameUseCase.Companion.NOT_MATCHED

class KMPSearchMatcher : SearchMatcher {
    override fun getMatchAccuracy(text: String, keyword: String): Int {
        if (keyword.isBlank())
            return NOT_MATCHED

        val formattedText = text.trim().lowercase()
        val formattedKeyword = keyword.trim().lowercase()

        val lps = constructLPS(formattedKeyword)
        val textLength = formattedText.length
        val keywordLength = formattedKeyword.length

        var i = 0
        var j = 0
        while (i < textLength) {
            if (formattedText[i] == formattedKeyword[j]) {
                i++
                j++
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
                j++
                lps[i] = j
                i++
            } else if (j != 0) {
                j = lps[j - 1]
            } else {
                lps[i] = 0
                i++
            }
        }

        return lps
    }
}