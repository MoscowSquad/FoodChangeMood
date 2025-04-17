package com.moscow.squad

import org.example.data.NOT_MATCHED
import org.example.logic.SearchMatcher
import kotlin.math.min

class FuzzySearchMatcher(private val threshold: Int = 2) : SearchMatcher {
    override fun getMatchAccuracy(text: String, keyword: String): Int {
        if (keyword.isBlank())
            return NOT_MATCHED

        val formattedText = text.trim().lowercase()
        val formattedKeyword = keyword.trim().lowercase()

        val depth = Array(formattedText.length + 1) { IntArray(formattedKeyword.length + 1) }

        for (i in 0..formattedText.length) {
            depth[i][0] = i
        }
        for (j in 0..formattedKeyword.length) {
            depth[0][j] = j
        }

        for (i in 1..formattedText.length) {
            for (j in 1..formattedKeyword.length) {
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

        val accuracy = depth[formattedText.length][formattedKeyword.length]
        return if (accuracy <= threshold) accuracy
        else NOT_MATCHED
    }
}