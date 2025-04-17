package org.example.data

import org.example.logic.SearchMatcher
import org.example.model.BlankKeywordException
import org.example.model.KeywordNotFoundException

const val MATCH_EXACTLY = 0
const val MATCH_PARTIAL = 1
const val NOT_MATCHED = -1

class SearchExecutor(
    private val matcher: SearchMatcher,
    private val list: List<String>,
) {
    fun search(keyword: String): String {
        if (keyword.isBlank())
            throw BlankKeywordException()

        val matchedList = mutableListOf<Pair<Int, String>>()
        list.forEach { text ->
            val accuracy = matcher.getMatchAccuracy(text, keyword)
            if (accuracy == MATCH_EXACTLY)
                return text
            else if (accuracy != NOT_MATCHED)
                matchedList.add(accuracy to text)
        }

        if (matchedList.isEmpty())
            throw KeywordNotFoundException(keyword = keyword)

        return matchedList.sortedBy { it.first }[0].second
    }
}