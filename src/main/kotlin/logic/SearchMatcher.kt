package org.example.logic

interface SearchMatcher {
    fun getMatchAccuracy(text: String, keyword: String): Int
}