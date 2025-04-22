package org.example.utils

fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
    return generateSequence { this.random() }
        .distinct()
        .take(numberOfMeals)
        .toList()
}