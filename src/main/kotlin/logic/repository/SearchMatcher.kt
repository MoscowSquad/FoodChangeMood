package org.example.logic.repository

interface SearchMatcher {
    fun getMatchAccuracy(text: String, keyword: String): Int
}