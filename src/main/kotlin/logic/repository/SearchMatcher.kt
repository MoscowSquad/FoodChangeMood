package org.example.logic.repository

import org.example.model.Exceptions

typealias Accuracy = Int

interface SearchMatcher {
    @Throws(
        Exceptions.EmptyKeywordException::class,
    )
    fun getMatchAccuracy(text: String, keyword: String): Accuracy
}