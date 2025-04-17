package org.example

import org.example.dependencyInjection.appModule
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(appModule)
    }

    val ui: FoodChangeMoodConsoleUI = getKoin().get()
    ui.start()
}