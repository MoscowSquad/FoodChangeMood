package org.example.utils

//fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
//    return generateSequence { this.random() }
//        .distinct()
//        .take(numberOfMeals)
//        .toList()
//}
fun <T> List<T>.takeRandomMeals(numberOfMeals: Int): List<T> {
    return toSet()
        .let { uniqueItems ->
            if (uniqueItems.size <= numberOfMeals) uniqueItems.toList()
            else generateSequence { uniqueItems.random() }
                .distinct()
                .take(numberOfMeals)
                .toList()
        }
}