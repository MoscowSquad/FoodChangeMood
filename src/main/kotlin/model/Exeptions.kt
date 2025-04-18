package org.example.model

class KeywordNotFoundException(val keyword: String) : Exception("No matches found for the text: $keyword")
class BlankKeywordException() : Exception("You can't search by blank value")