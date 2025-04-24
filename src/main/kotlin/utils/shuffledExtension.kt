package org.example.utils

import kotlin.random.Random

fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
    return generateSequence { Random.nextInt(this.size) }
        .distinct()
        .take(numberOfMeals.coerceAtMost(this.size)).also { println(it) }
        .toList()
        .map(this::get)
}