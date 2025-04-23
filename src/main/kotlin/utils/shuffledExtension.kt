package org.example.utils

import kotlin.random.Random

fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
    return generateSequence { Random.nextInt(numberOfMeals) }
        .distinct()
        .take(numberOfMeals)
        .toList()
        .map(this::get)
}